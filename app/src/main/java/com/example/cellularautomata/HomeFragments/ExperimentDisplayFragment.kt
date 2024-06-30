package com.example.cellularautomata.HomeFragments

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.cellularautomata.databinding.FragmentExperimentDisplayBinding

class ExperimentDisplayFragment : Fragment() {

    private var experimentNumber: Int = 0
    private lateinit var experimentName: String
    private lateinit var binding: FragmentExperimentDisplayBinding
    private var globalGeneration: Int = 100
    private var globalSize: Int = 100
    private lateinit var selectedColour :String

    // Request code for permission request
    private val PERMISSION_REQUEST_CODE = 101

    // Permission launcher for requestPermission contract
    private val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, proceed to download image
                binding.imageGenerate.drawable?.let { drawable ->
                    val bitmap = (drawable).toBitmap()
                    downloadImage(bitmap)
                }
            } else {
                Log.e("Permission", "WRITE_EXTERNAL_STORAGE permission denied")
                Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Python with AndroidPlatform
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExperimentDisplayBinding.inflate(inflater, container, false)

        experimentNumber = arguments?.getInt("experiment_number") ?: 0
        val value_name = arguments?.getString("experiment_name") ?: ""

        val regex = Regex("^\\d+\\.\\s*")
        experimentName = regex.replaceFirst(value_name, "")
        experimentNumber += 1
        binding.experimentNumber.text = "Experiment No.: $experimentNumber"
        binding.experimentName.text = experimentName

        if (experimentNumber == 1 || experimentNumber == 2) {
            binding.ruleNumber.visibility = View.VISIBLE
            binding.generationNumber.visibility = View.VISIBLE
            binding.sizeOfGeneration.visibility = View.VISIBLE
            binding.generatebtn.visibility = View.VISIBLE
            binding.generationNumber.hint = "Enter Generation number (1-$globalGeneration)"
            binding.sizeOfGeneration.hint = "Enter Column Size (1-$globalSize)"
            binding.TextInputLayout.visibility = View.VISIBLE
            binding.AutoCompleteTextView.visibility = View.VISIBLE

            val languages = listOf( "binary","inferno","viridis", "plasma", "magma", "cividis", "Greys", "Blues", "Reds", "hot", "cool")

            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, languages)
            binding.AutoCompleteTextView.setAdapter(adapter)

            selectedColour="binary"

            binding.AutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                selectedColour= parent.getItemAtPosition(position).toString()
            }

            binding.generatebtn.setOnClickListener {
                val ruleNumber = binding.ruleNumber.text.toString().toIntOrNull()
                val sizeOfGeneration = binding.sizeOfGeneration.text.toString().toIntOrNull()
                val generationNumber = binding.generationNumber.text.toString().toIntOrNull()
                val randomInitialState = experimentNumber == 2


                if (ruleNumber != null && sizeOfGeneration != null && generationNumber != null &&
                    ruleNumber in 0..255 && generationNumber in 1..globalGeneration && sizeOfGeneration in 1..globalSize
                ) {
                    generateAutomataExp1And2(ruleNumber, sizeOfGeneration, generationNumber, randomInitialState,selectedColour)
                } else {
                    Toast.makeText(requireContext(), "Invalid entry", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (experimentNumber == 3|| experimentNumber == 4) {
            binding.ruleNumber.visibility = View.VISIBLE
            binding.generationNumber.visibility = View.VISIBLE
            binding.sizeOfGeneration.visibility = View.VISIBLE
            binding.generatebtn.visibility = View.VISIBLE
            binding.generationNumber.hint = "Enter Generation number (1-$globalGeneration)"
            binding.sizeOfGeneration.hint = "Enter Column Size (1-$globalSize)"
            binding.TextInputLayout.visibility = View.VISIBLE
            binding.AutoCompleteTextView.visibility = View.VISIBLE

            val languages = listOf( "binary","inferno","viridis", "plasma", "magma", "cividis", "Greys", "Blues", "Reds", "hot", "cool")

            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, languages)
            binding.AutoCompleteTextView.setAdapter(adapter)

            selectedColour="binary"

            binding.AutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                selectedColour= parent.getItemAtPosition(position).toString()
            }

            binding.generatebtn.setOnClickListener {
                val ruleNumber = binding.ruleNumber.text.toString().toIntOrNull()
                val sizeOfGeneration = binding.sizeOfGeneration.text.toString().toIntOrNull()
                val generationNumber = binding.generationNumber.text.toString().toIntOrNull()
                val randomInitialState = experimentNumber == 4


                if (ruleNumber != null && sizeOfGeneration != null && generationNumber != null &&
                    ruleNumber in 0..255 && generationNumber in 1..globalGeneration && sizeOfGeneration in 1..globalSize
                ) {
                    generateAutomataExp3and4(ruleNumber, sizeOfGeneration, generationNumber, randomInitialState,selectedColour)
                } else {
                    Toast.makeText(requireContext(), "Invalid entry", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun generateAutomataExp3and4(ruleNumber: Int, sizeOfGeneration: Int, generationNumber: Int, randomInitialState: Boolean, selectedColour: String) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("cellular_automaton_generator2")

        val result: PyObject = pythonFile.callAttr(
            "generate_and_return_image",
            ruleNumber,
            sizeOfGeneration,
            generationNumber,
            randomInitialState,
            experimentName,
            "Size",
            "Generation",
            selectedColour,
            10
        )

        val imgStrAutomaton = result.asList().get(0).toString()
        val imgStrCombined = result.asList().get(1).toString()
        val decodedStringAutomaton: ByteArray = Base64.decode(imgStrAutomaton, Base64.DEFAULT)
        val decodedStringCombined: ByteArray = Base64.decode(imgStrCombined, Base64.DEFAULT)
        val bitmapAutomaton = BitmapFactory.decodeByteArray(decodedStringAutomaton, 0, decodedStringAutomaton.size)
        val bitmapCombined = BitmapFactory.decodeByteArray(decodedStringCombined, 0, decodedStringCombined.size)


        binding.imageGenerate.visibility = View.VISIBLE
        binding.imageGenerate2.visibility = View.VISIBLE
        binding.downloadbtn.visibility = View.VISIBLE
        binding.imageGenerate.setImageBitmap(bitmapAutomaton)
        binding.imageGenerate2.setImageBitmap(bitmapCombined)

        binding.downloadbtn.setOnClickListener {
            checkAndRequestPermission()
        }
    }
    private fun generateAutomataExp1And2(ruleNumber: Int, sizeOfGeneration: Int, generationNumber: Int, randomInitialState: Boolean, selectedColour: String) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("cellular_automaton_generator")

//        generate_and_return_image(rule_number, size, generations, random_initial_state,title="",X_axis="",Y_axiz="", colormap='binary', cell_size=10)
        val result: PyObject = pythonFile.callAttr(
            "generate_and_return_image",
            ruleNumber,
            sizeOfGeneration,
            generationNumber,
            randomInitialState,
            experimentName,
            "Size",
            "Generation",
            selectedColour,
            10
        )

        val imgStr = result.toString()
        val decodedString: ByteArray = Base64.decode(imgStr, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        binding.imageGenerate.visibility = View.VISIBLE
        binding.downloadbtn.visibility = View.VISIBLE
        binding.imageGenerate.setImageBitmap(bitmap)
        binding.downloadbtn.setOnClickListener {
            checkAndRequestPermission()
        }
    }

    private fun checkAndRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, proceed to download
                binding.imageGenerate.drawable?.let { drawable ->
                    val bitmap = (drawable).toBitmap()
                    downloadImage(bitmap)
                }
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                // Explain to the user why you need the permission and then request it
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> {
                // Request the permission directly
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun downloadImage(bitmap: Bitmap) {
        // Retrieve the bitmap from ImageView
        // Save image to the device's gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "Cellular Automaton",
            "Image of Cellular Automaton"
        )

        // Show toast based on the save result
        if (savedImageURL != null) {
            Toast.makeText(requireContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
        }
    }
}
