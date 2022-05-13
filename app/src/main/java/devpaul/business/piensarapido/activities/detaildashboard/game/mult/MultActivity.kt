package devpaul.business.piensarapido.activities.detaildashboard.game.mult

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import devpaul.business.piensarapido.R
import kotlin.random.Random

class MultActivity : AppCompatActivity() {

    var op1: Int? = null
    var op2: Int? = null
    var mult: Int? = null
    var multOther: Int? = null

    var tvTimer: TextView? = null
    var tvPoints: TextView? = null
    var tvSum: TextView? = null
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

    var textlevel : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mult)

        //desactivar rotacion pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        op1 = 0
        op2 = 0
        mult = 0
        tvTimer = findViewById(R.id.tvTimer)
        tvPoints = findViewById(R.id.tvPoints)
        tvSum = findViewById(R.id.tvSum)
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
        if (level == "one"){
            fistlevelGame()
            textlevel?.text = "Nivel 1"
        }
        if (level == "two"){
            secondLevel()
            textlevel?.text = "Nivel 2"
        }
        if (level == "third"){
            thirdLevel()
            textlevel?.text = "Nivel 3"
        }

        if (level == "four") {
            fourthLevel()
            textlevel?.text = "Nivel 4"
        }


    }

    @SuppressLint("SetTextI18n")
    private fun fistlevelGame() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestion()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@MultActivity, GameOverMultiplicacionActivity::class.java)
                intent.putExtra("points", points)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestion() {
        numberofQuestions++
        op1 = random.nextInt(10)
        op2 = random.nextInt(10)
        mult = op1!! * op2!!
        tvSum?.text = "$op1 * $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + mult
        while (true) {
            if (incorrectAnswers!!.size > 3) break
            op1 = random.nextInt(10)
            op2 = random.nextInt(10)
            multOther = op1!! * op2!!
            if (multOther == mult) {
                continue
            }
            incorrectAnswers?.add(multOther!!)
        }

        for (i in 0..2) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }

    fun chooseAnswer(view: View) {
        val answer = (view as Button).text.toString().toInt()
        if (answer == mult) {
            points++
            tvResult!!.text = "Correcto!"
        } else {
            tvResult!!.text = "Incorrecto!"
        }
        tvPoints!!.text = "$points/$numberofQuestions"
        val level = intent.getStringExtra("level")
        if (level == "one"){
            generateQuestion()
        }

        if (level == "two"){
            generateQuestion2()
        }

        if (level == "third"){
            generateQuestion3()
        }

        if (level == "four"){
            generateQuestion4()
        }

    }

    //SecondLevel
    @SuppressLint("SetTextI18n")
    private fun secondLevel() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestion2()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@MultActivity, GameOverMultiplicacionActivity::class.java)
                intent.putExtra("points", points)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestion2() {
        numberofQuestions++
        op1 = random.nextInt(8,15)
        op2 = random.nextInt(8,15)
        mult = op1!! * op2!!
        tvSum?.text = "$op1 * $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + mult
        while (true) {
            if (incorrectAnswers!!.size > 3) break
            op1 = random.nextInt(8,15)
            op2 = random.nextInt(8,15)
            multOther = op1!! * op2!!
            if (multOther == mult) {
                continue
            }
            incorrectAnswers?.add(multOther!!)
        }

        for (i in 0..2) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }

    //ThirdLevel
    @SuppressLint("SetTextI18n")
    private fun thirdLevel() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestion3()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@MultActivity, GameOverMultiplicacionActivity::class.java)
                intent.putExtra("points", points)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestion3() {
        numberofQuestions++
        op1 = random.nextInt(15,25)
        op2 = random.nextInt(15,25)
        mult = op1!! * op2!!
        tvSum?.text = "$op1 * $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + mult
        while (true) {
            if (incorrectAnswers!!.size > 3) break
            op1 = random.nextInt(15,25)
            op2 = random.nextInt(15,25)
            multOther = op1!! * op2!!
            if (multOther == mult) {
                continue
            }
            incorrectAnswers?.add(multOther!!)
        }

        for (i in 0..2) {
            if (i == correctAnswerPosition){
                continue
            }
            (findViewById<View>(btnIds[i]) as Button).text = "" + incorrectAnswers!![i]
        }
        incorrectAnswers!!.clear()
    }

    //FourthLevel
    @SuppressLint("SetTextI18n")
    private fun fourthLevel() {

        tvTimer?.text = "" + (millisUntilFinished!! / 1000) + "s"
        tvPoints?.text = "$points/$numberofQuestions"
        generateQuestion4()
        countDownTimer = object : CountDownTimer(millisUntilFinished!!, 1000) {
            override fun onTick(time: Long) {
                tvTimer?.text = "" + (time / 1000) + "s"
            }

            override fun onFinish() {
                btn0?.isClickable = false
                btn1?.isClickable = false
                btn2?.isClickable = false
                btn3?.isClickable = false
                val intent = Intent(this@MultActivity, GameOverMultiplicacionActivity::class.java)
                intent.putExtra("points", points)
                startActivity(intent)
                finish()
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun generateQuestion4() {
        numberofQuestions++
        op1 = random.nextInt(20,30)
        op2 = random.nextInt(15,19)
        mult = op1!! * op2!!
        tvSum?.text = "$op1 * $op2 = "
        correctAnswerPosition = random.nextInt(4)
        (findViewById<View>(btnIds[correctAnswerPosition]) as Button).text = "" + mult
        while (true) {
            if (incorrectAnswers!!.size > 3) break
            op1 = random.nextInt(20,30)
            op2 = random.nextInt(15,19)
            multOther = op1!! * op2!!
            if (multOther == mult) {
                continue
            }
            incorrectAnswers?.add(multOther!!)
        }

        for (i in 0..2) {
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