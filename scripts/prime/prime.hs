main = print $ eratosthenes 100

eratosthenes :: Integral a => a -> [a]
eratosthenes a = furui [2..a]

furui :: Integral a => [a] -> [a]
furui (x:xs) | head(reverse xs) > x * x  = x : furui (filter (\n -> n `mod` x /= 0) xs)
furui (x:xs) = x:xs
furui _ = []
