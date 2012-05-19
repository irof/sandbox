import Test.HUnit
import Test.Hspec.Monadic
import Test.Hspec.HUnit

fizzBuzz :: Integer -> String
fizzBuzz i | i `mod` 15 == 0 = "FizzBuzz"
fizzBuzz i | i `mod` 3 == 0 = "Fizz"
fizzBuzz i | i `mod` 5 == 0 = "Buzz"
fizzBuzz i = show i

main = hspecX $ do
  describe "FizzBuzz" $ do
    it "returns '1' when given 1" $
      fizzBuzz 1 @?= "1"
    it "returns 'Fizz' when given 3" $
      fizzBuzz 3 @?= "Fizz"
    it "returns 'Buzz' when given 5" $
      fizzBuzz 5 @?= "Buzz"
    it "returns 'FizzBuzz' when given 15" $
      fizzBuzz 15 @?= "FizzBuzz"
