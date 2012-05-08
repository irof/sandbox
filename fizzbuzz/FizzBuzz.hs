import Test.HUnit

fizzBuzz :: Integer -> String
fizzBuzz i | i `mod` 15 == 0 = "FizzBuzz"
fizzBuzz i | i `mod` 3 == 0 = "Fizz"
fizzBuzz i | i `mod` 5 == 0 = "Buzz"
fizzBuzz i = show i

tests =
    ("3 Fizz"      ~: "Fizz"     ~=? (fizzBuzz 3))
  : ("5 Buzz"      ~: "Buzz"     ~=? (fizzBuzz 5))
  : ("6 Fizz"      ~: "Fizz"     ~=? (fizzBuzz 6))
  : ("1 1"         ~: "1"        ~=? (fizzBuzz 1))
  : ("7 7"         ~: "7"        ~=? (fizzBuzz 7))
  : ("15 FizzBuzz" ~: "FizzBuzz" ~=? (fizzBuzz 15))
  : []

main = runTestTT $ TestList tests
