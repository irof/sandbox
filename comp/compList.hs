import Test.HUnit
import Test.Hspec.Monadic
import Test.Hspec.HUnit

leftOnly ls rs = filter (\a -> notElem a rs) ls
rightOnly ls rs = filter (\a -> notElem a ls) rs
both ls rs = filter (\a -> elem a rs) ls

compList ls rs = do
  putStrLn $ "[Left Only ] " ++ leftOnly ls rs
  putStrLn $ "[Right Only] " ++ rightOnly ls rs
  putStrLn $ "[Both      ] " ++ both ls rs


main = hspecX $ do
  describe "compList" $ do
    describe "left only" $ do
      it "returns [1] when given [1,2] [2]" $
        leftOnly [1,2] [2] @?= [1]

      it "returns [1,3] when given [1,3] [2]" $
        leftOnly [1,3] [2] @?= [1,3]
    
    describe "right only" $ do
      it "returns [b,d] when given [1,c,a] [a,b,c,d]" $
        rightOnly ['1','c','a'] ['a','b','c','d'] @?= "bd"
    
    describe "both" $ do
      it "returns [2,3] when given [1,2,3] [2,3,4]" $
        both [1,2,3] [2,3,4] @?= [2,3]

  describe "Learning Test" $ do
    it "filter list" $ do
      filter (\ a -> False) [1,2,3] @?= []

    it "elem list" $ do
      elem 2 [1,2,3] @?= True

    it "elem list" $ do
      elem 4 [1,2,3] @?= False
