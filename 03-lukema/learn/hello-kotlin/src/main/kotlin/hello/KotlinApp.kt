package hello

// import hello.kotlin.App;

class KotlinApp {
  public fun getGreeting(): String {
    return "1. Hello world from KotlinApp!"
  }
}

fun main() {
  // println(App().getGreeting())
  println(KotlinApp().getGreeting())
  println("2. Hello world from kotlin main()!")
}
