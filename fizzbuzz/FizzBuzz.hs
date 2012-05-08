import Test.HUnit

fizzBuzz :: Integer -> String
fizzBuzz i | i `mod` 15 == 0 = "FizzBuzz"
fizzBuzz i | i `mod` 3 == 0 = "Fizz"
fizzBuzz i | i `mod` 5 == 0 = "Buzz"
fizzBuzz i = show i

tests =
    TestCase (assertEqual "3でFizz"      "Fizz"     (fizzBuzz 3))
  : TestCase (assertEqual "5でBuzz"      "Buzz"     (fizzBuzz 5))
  : TestCase (assertEqual "6でFizz"      "Fizz"     (fizzBuzz 6))
  : TestCase (assertEqual "1は1"         "1"        (fizzBuzz 1))
  : TestCase (assertEqual "7は7"         "7"        (fizzBuzz 7))
  : TestCase (assertEqual "15はFizzBuzz" "FizzBuzz" (fizzBuzz 15))
  : []

main = runTestTT $ TestList tests
