package DataClass

object Constants {

    //total material
    const val totalCourses=8
    const val totalQuiz=10




    const val selectedOption=0
    const val TOTAL_QUESTION = "total_questions"
    const val CORRECT_ANSWER = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()

        val q1 = Question(
            1,
            "What is a cellular automaton?",
            null,
            "A type of autonomous robot.",
            "A mathematical model for a system of cell-like entities evolving in discrete steps.",
            "A machine that performs cellular functions in biology.",
            "A software application for mobile phones.",
            2
        )
        val q2 = Question(
            2,
            "Who is considered the pioneer of cellular automata theory?",
            null,
            "Alan Turing",
            "Stephen Wolfram",
            "John von Neumann",
            "John Conway",
            3
        )
        val q3 = Question(
            3,
            "Which of the following is a famous example of a cellular automaton?",
            null,
            "Turing machine",
            "Game of Life",
            "Monte Carlo simulation",
            "Neural network",
            2
        )
        val q4 = Question(
            4,
            "In the Game of Life, what happens to a live cell with two or three live neighbors?",
            null,
            "It dies.",
            "It stays alive.",
            "It becomes a dead cell.",
            "It transforms into two live cells.",
            2
        )
        val q5 = Question(
            5,
            "In a one-dimensional cellular automaton, how many possible states can a single cell have in the simplest case?",
            null,
            "1",
            "2",
            "4",
            "8",
            2
        )
        val q6 = Question(
            6,
            "What is the main characteristic of a rule in cellular automata?",
            null,
            "It defines the initial state of the system.",
            "It determines how cells interact with each other and evolve over time.",
            "It sets the size of the cellular grid.",
            "It specifies the color of each cell.",
            2
        )
        val q7 = Question(
            7,
            "In cellular automata, what is the term for the set of cells that a given cell interacts with?",
            null,
            "Neighborhood",
            "Community",
            "Group",
            "Cluster",
            1
        )
        val q8 = Question(
            8,
            "What is the name of the neighborhood used in the Game of Life?",
            null,
            "Moore neighborhood",
            "von Neumann neighborhood",
            "Hexagonal neighborhood",
            "Cellular neighborhood",
            1
        )
        val q9 = Question(
            9,
            "Which of the following is NOT a common use of cellular automata?",
            null,
            "Modeling biological systems",
            "Simulating traffic flow",
            "Image processing",
            "Compiling computer programs",
            4
        )
        val q10 = Question(
            10,
            "What type of boundary condition in cellular automata assumes the edges are connected like a torus?",
            null,
            "Fixed boundary",
            "Reflective boundary",
            "Periodic boundary",
            "Absorbing boundary",
            3
        )

        questionList.addAll(listOf(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10))
        return questionList
    }


}
