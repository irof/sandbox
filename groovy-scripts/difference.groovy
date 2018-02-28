@Grab(group='org.apache.commons', module='commons-lang3', version='3.7')
import org.apache.commons.lang3.StringUtils

def str1 = 'hoge.fuga.Foo'
def str2 = 'hoge.fuga.Bar'
def str3 = 'hoge.fuga.Baz'

println StringUtils.difference(str1, str2)
println StringUtils.difference(str2, str3)

println StringUtils.indexOfDifference(str2, str3)

