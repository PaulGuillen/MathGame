package devpaul.business.piensarapido.activities.detaildashboard.game.rest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import devpaul.business.piensarapido.R
import kotlin.random.Random

class RestActivity : AppCompatActivity() {


    var op1: Int? = null
    var op2: Int? = null
    var rest: Int? = null
    var restOther: Int? = null

    var tvTimer: TextView? = null
    var tvPoints: TextView? = null
    var tvRest: TextView? = null
    var tvResult: TextView? = null
    var btn0: Button? = null
    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null

    var countDownTimer: CountDownTimer? = null
    var millisUntilFinished: Long? = null

    lateinit var btnIds: IntArray
    var correctAnswerPosition = 0
    var incorrectAnswers: ArrayList<Int>? = null

    var points =0
    var numberofQuestions = 0
    var random: Random = Random

    var textlevel : TextView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        op1 = 0
        op2 = 0
        rest = 0
        tvTimer = findViewById(R.id.tvTimer)
        tvPoints = findViewById(R.id.tvPoints)
        tvRest = findViewById(R.id.tvSum)
        tvResult = findViewById(R.id.tvResult)

        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)

        textlevel = findViewById(R.id.text_level)

        btnIds = intArrayOf(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3)
        correctAnswerPosition = 0
        incorrectAnswers = ArrayList()
        millisUntilFinished = 30100
        incorrectAnswers = ArrayList()

        val level = intent.getStringExtra("level")
        if (level == "facil"){
            easylevelgame()
            textlevel?.text = "Nivel F??cil"
        }
        if (level == "intermedio"){
            intermediateLevel()
            textlevel?.text = "Nivel Intermedio"
        }
        if (level == "avanzado"){
            advancedlevel()
            textlevel?.text = "Nivel Avanzado"
        }

        if (level == "experto") {
            expertlevel()
            textlevel?.text = "Nivel Experto"
        }

    }

    @SuppressLint("SetTextI18n")
    private fun easylevelgame() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestionEasy()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@RestActivity, GameOverRestActivity::class.java)
                val incorrectAnswers = numberofQuestions - points
                val level = intent.getStringExtra("level")
                intent.putExtra("level", "Facil")
                intent.putExtra("points", points)
                intent.putExtra("incorrectAnswers", incorrectAnswers)
                intent.putExtra("numberQuestions", numberofQuestions)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestionEasy() {
        numberofQuestions++
        op1 = random.nextInt(20,45)
        op2 = random.nextInt(5,19)
        rest = op1!! - op2!!
        tvRest?.text = "$op1 - $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + rest
        while (true) {
            if (incorrectAnswers!!.size > 3) break
            op1 = random.nextInt(20,45)
            op2 = random.nextInt(5,19)
            restOther = op1!! - op2!!
            if (restOther == rest) {
                continue
            }
            incorrectAnswers?.add(restOther!!)
        }

        for (i in 0..3) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }

    fun chooseAnswer(view: View) {
        val answer = (view as Button).text.toString().toInt()
        if (answer == rest) {
            points++
            tvResult!!.text = "Correcto!"
        } else {
            tvResult!!.text = "Incorrecto!"
        }
        tvPoints!!.text = "$points/$numberofQuestions"
        val level = intent.getStringExtra("level")
        if (level == "facil"){
            generateQuestionEasy()
        }

        if (level == "intermedio"){
            generateQuestionIntermediate()
        }

        if (level == "avanzado"){
            generateQuestionAdvanced()
        }

        if (level == "experto"){
            generateQuestionExpert()
        }

    }

    //SecondLevel
    @SuppressLint("SetTextI18n")
    private fun intermediateLevel() {
        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestionIntermediate()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@RestActivity, GameOverRestActivity::class.java)
                val incorrectAnswers = numberofQuestions - points
                val level = intent.getStringExtra("level")
                intent.putExtra("level", "Intermedio")
                intent.putExtra("points", points)
                intent.putExtra("incorrectAnswers", incorrectAnswers)
                intent.putExtra("numberQuestions", numberofQuestions)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestionIntermediate() {
        numberofQuestions++
        op1 = random.nextInt(50,99)
        op2 = random.nextInt(12,49)
        rest = op1!! - op2!!
        tvRest?.text = "$op1 - $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + rest
        while (true) {
            if (incorrectAnswers!!.size > 3) break
            op1 = random.nextInt(50,99)
            op2 = random.nextInt(12,49)
            restOther = op1!! - op2!!
            if (restOther == rest) {
                continue
            }
            incorrectAnswers?.add(restOther!!)
        }

        for (i in 0..3) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }

    //ThirdLevel
    @SuppressLint("SetTextI18n")
    private fun advancedlevel() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestionAdvanced()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@RestActivity, GameOverRestActivity::class.java)
                val incorrectAnswers = numberofQuestions - points
                val level = intent.getStringExtra("level")
                intent.putExtra("level", "Avanzado")
                intent.putExtra("points", points)
                intent.putExtra("incorrectAnswers", incorrectAnswers)
                intent.putExtra("numberQuestions", numberofQuestions)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestionAdvanced() {
        numberofQuestions++
        op1 = random.nextInt(105,180)
        op2 = random.nextInt(30,104)
        rest = op1!! - op2!!
        tvRest?.text = "$op1 - $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + rest
        while (true) {
            if (incorrectAnswers!!.size > 4) break
            op1 = random.nextInt(105,180)
            op2 = random.nextInt(30,104)
            restOther = op1!! - op2!!
            if (restOther == rest) {
                continue
            }
            incorrectAnswers?.add(restOther!!)
        }

        for (i in 0..3) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }

    //FourthLevel
    @SuppressLint("SetTextI18n")
    private fun expertlevel() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestionExpert()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@RestActivity, GameOverRestActivity::class.java)
                val incorrectAnswers = numberofQuestions - points
                val level = intent.getStringExtra("level")
                intent.putExtra("level", "Experto")
                intent.putExtra("points", points)
                intent.putExtra("incorrectAnswers", incorrectAnswers)
                intent.putExtra("numberQuestions", numberofQuestions)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestionExpert() {
        numberofQuestions++
        op1 = random.nextInt(199,310)
        op2 = random.nextInt(55,190)
        rest = op1!! - op2!!
        tvRest?.text = "$op1 - $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + rest
        while (true) {
            if (incorrectAnswers!!.size > 4) break
            op1 = random.nextInt(199,310)
            op2 = random.nextInt(55,190)
            restOther = op1!! - op2!!
            if (restOther == rest) {
                continue
            }
            incorrectAnswers?.add(restOther!!)
        }

        for (i in 0..3) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        countDownTimer?.cancel()
    }


}