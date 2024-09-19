package Views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class GameOfLifeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rows = 50
    private var cols = 50
    private var grid = Array(rows) { IntArray(cols) }
    private val paint = Paint()
    private val handler = Handler(Looper.getMainLooper())
    private var updateInterval = 1000L // milliseconds
    private var isRunning = false
    private var colorScheme: String = "binary"
    private var darkColor: String = "#000000" // Default to black
    private var lightColor: String = "#FFFFFF" // Default to white
    private var gameFunctionName1=""

    init {
        initializePython()
//        initializeGrid(0.2)
        paint.style = Paint.Style.FILL
    }

    private fun initializePython() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }
    }
    fun refresh() {
        initializeGrid(0.2)
        invalidate()
    }

    private fun initializeGrid(probAlive: Double) {
        try {
            val py = Python.getInstance()
            val gameOfLifeModule = py.getModule("gameOfLifeNew")
            var pyGrid: Array<IntArray>
            if(gameFunctionName1=="apply_game_of_life_rule_glider")
            {
                pyGrid  = gameOfLifeModule.callAttr("initialize_grid", rows, cols, probAlive,"glider").toJava(Array<IntArray>::class.java)
            }
            else if(gameFunctionName1=="apply_game_of_life_rule_pulsar")
            {
//                rows = 17
//                cols = 17
//                grid = Array(rows) { IntArray(cols) }
                pyGrid  = gameOfLifeModule.callAttr("initialize_grid", rows, cols, probAlive,"pulsar").toJava(Array<IntArray>::class.java)
            }
            else if(gameFunctionName1=="apply_game_of_life_rule_glider_gun")
            {
                pyGrid  = gameOfLifeModule.callAttr("initialize_grid", rows, cols, probAlive,"glidergun").toJava(Array<IntArray>::class.java)
            }
            else
            {
                pyGrid = gameOfLifeModule.callAttr("initialize_grid", rows, cols, probAlive,"randomgrid").toJava(Array<IntArray>::class.java)
            }
            Log.e("pyGrid", pyGrid.contentDeepToString())

            for (i in 0 until rows) {
                grid[i] = pyGrid[i]
            }
        } catch (e: Exception) {
            Log.e("GameOfLifeView", "Error initializing grid", e)
        }
    }

    private fun applyGameOfLifeRule() {
        Log.e("rows: ",rows.toString())
        Log.e("cols: ",cols.toString())
        try {
            Log.e("function name", gameFunctionName1)
            val py = Python.getInstance()
            val gameOfLifeModule = py.getModule("gameOfLifeNew")
            val pyGrid = gameOfLifeModule.callAttr(gameFunctionName1, grid).toJava(Array<IntArray>::class.java)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    grid[i][j] = pyGrid[i][j]
                }
            }
        } catch (e: Exception) {
            Log.e("GameOfLifeView", "Error applying Game of Life rule", e)
        }
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                applyGameOfLifeRule()
                invalidate()
                handler.postDelayed(this, updateInterval)
            }
        }
    }

    fun startAnimation(gameFunctionName:String) {
        gameFunctionName1=gameFunctionName
        if (!isRunning) {
            isRunning = true
            handler.post(updateRunnable)
        }
    }
    fun init_gamename(gameFunctionName:String) {
        gameFunctionName1=gameFunctionName
        initializeGrid(0.2)
    }
    fun stopAnimation() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(updateRunnable)
        }
    }

    fun setColorScheme(scheme: String) {
        colorScheme = scheme
        try {
            val py = Python.getInstance()
            val gameOfLifeModule = py.getModule("gameOfLifeNew")
            val colors: PyObject = gameOfLifeModule.callAttr("get_schema_color_code", scheme)
            darkColor = colors.asList()[0].toString()
            lightColor = colors.asList()[1].toString()
        } catch (e: Exception) {
            Log.e("GameOfLifeView", "Error setting color scheme", e)
        }
        invalidate()
    }
    fun setSpeed(speed: String) {
        updateInterval = when (speed) {
            "0.25" -> 2000
            "0.50" -> 1500
            "0.75" -> 1250
            "1.0"  -> 1000
            "1.5"  -> 750
            "2.0"  -> 500
            "2.5"  -> 250
            "3.0"  -> 100
            else   -> 1000
        }
        Log.e("speed", updateInterval.toString())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cellWidth = width.toFloat() / cols
        val cellHeight = height.toFloat() / rows
        Log.e("Colour", "Color scheme: $colorScheme")
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                paint.color = if (grid[i][j] == 1) Color.parseColor(darkColor) else Color.parseColor(lightColor)
                canvas.drawRect(
                    j * cellWidth, i * cellHeight,
                    (j + 1) * cellWidth, (i + 1) * cellHeight, paint
                )
            }
        }
    }

}
