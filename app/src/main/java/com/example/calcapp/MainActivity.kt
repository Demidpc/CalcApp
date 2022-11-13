package com.example.calcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.calcapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass:ActivityMainBinding

    // добавляем контроллер двойного оператора (для =, -, /, *)
    var operationController = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // "надуваем" binding класс (создаём для него переменную)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        // в Content View теперь лежат все элементы из root
        setContentView(bindingClass.root)


        //назначение кнопкам символы
        bindingClass.btn0.setOnClickListener { setTextField("0") }                                                                 // 0
        bindingClass.btn1.setOnClickListener { setTextField("1") }                                                                 // 1
        bindingClass.btn2.setOnClickListener { setTextField("2") }                                                                 // 2
        bindingClass.btn3.setOnClickListener { setTextField("3") }                                                                 // 3
        bindingClass.btn4.setOnClickListener { setTextField("4") }                                                                 // 4
        bindingClass.btn5.setOnClickListener { setTextField("5") }                                                                 // 5
        bindingClass.btn6.setOnClickListener { setTextField("6") }                                                                 // 6
        bindingClass.btn7.setOnClickListener { setTextField("7") }                                                                 // 7
        bindingClass.btn8.setOnClickListener { setTextField("8") }                                                                 // 8
        bindingClass.btn9.setOnClickListener { setTextField("9") }                                                                 // 9
        bindingClass.plusBtn.setOnClickListener { if (!operationController){setTextField("+")}; operationController = true }       // +
        bindingClass.minusBtn.setOnClickListener { if (!operationController){setTextField("-")}; operationController = true }      // -
        bindingClass.miltiplyBtn.setOnClickListener { if (!operationController){setTextField("*")}; operationController = true }   // *
        bindingClass.delenieBtn.setOnClickListener { if (!operationController){setTextField("/")}; operationController = true }    // /
        bindingClass.openBtn.setOnClickListener { setTextField("(") }                                                              // (
        bindingClass.closeBtn.setOnClickListener { setTextField(")") }                                                             // )
        bindingClass.zptBtn.setOnClickListener { setTextField(",") }                                                               // ,
        bindingClass.cBtn.setOnClickListener {
            bindingClass.mathOperation.text = ""
            bindingClass.resultText.text = ""
            operationController = false
        }
        bindingClass.backBtn.setOnClickListener {
            var str = bindingClass.mathOperation.text.toString()
            if (str.isNotEmpty()){
                bindingClass.mathOperation.text = str.substring(0, str.length-1)
                operationController = false
            }
            else
                bindingClass.resultText.text = ""


        }
        bindingClass.resultBtn.setOnClickListener {
            try { //если всё работает хорошо

                bindingClass.resultText.text = "" // чистим окно результата

                //назначаем константу с модуоем от подключенной библиотеки
                val ex = ExpressionBuilder(bindingClass.mathOperation.text.toString()).build()

                //создаём константу, которая будет равна верхней константе с модификатором evaluate(решение примера)
                val result = ex.evaluate() // результат выходит с типом Double

                //создаём константу, которая будет равна верхней константе, переведённая в тип long (т.е длинное целое число)
                val longRes = result.toLong() // убираем числа после запятой
                if (result == longRes.toDouble()){ //если результат который у нас получился равен ранее созданной константе, переведённой в тип double (т.е с большим количеством чисел после запятой)
                    bindingClass.mathOperation.text = longRes.toString() // то меняем наш текст на ответ без чисел поле запятой
                }
                else {
                    bindingClass.mathOperation.text = result.toString() // иначе меняем наш текст на ответ с числами псле запятой
                }

            } catch (e:Exception){ //проверка на ошибки
                Log.d("Error", "Сообщение ${e.message}")
                bindingClass.mathOperation.text = "Ошибка"
            }
        }

    }

    // одинокая функция(  ей грустно
    fun setTextField(str: String){ // получаем строку и записываем её в переменную str
        bindingClass.mathOperation.append(str) // добавляем на строку наш символ str
        try { //если всё работает хорошо

            //назначаем константу с модуоем от подключенной библиотеки
            val ex = ExpressionBuilder(bindingClass.mathOperation.text.toString()).build()

            //создаём константу, которая будет равна верхней константе с модификатором evaluate(решение примера)
            val result = ex.evaluate() // результат выходит с типом Double

            //создаём константу, которая будет равна верхней константе, переведённая в тип long (т.е длинное целое число)
            val longRes = result.toLong() // убираем числа после запятой
            if (result == longRes.toDouble()){ //если результат который у нас получился равен ранее созданной константе, переведённой в тип double (т.е с большим количеством чисел после запятой)
                bindingClass.resultText.text = longRes.toString() // то меняем наш текст на ответ без чисел поле запятой
            }
            else {
                bindingClass.resultText.text = result.toString() // иначе меняем наш текст на ответ с числами псле запятой
            }

        } catch (e:Exception){ //проверка на ошибки
            Log.d("Error", "Сообщение ${e.message}")
        }
        operationController = false // обнуляем контроллер двойного оператора
    }

}