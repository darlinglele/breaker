Breaker基于正向最大匹配中文分词程序(Java8版)
=======
####词典结构--前缀树
前缀树常常也成为词典树，是保证词典高效查询的关键。前缀树经常也用来实现搜索关键字的Auto complete功能。
*   主要的包: com.pwc.dictionary
*   主要的类：com.pwc.dictionary.Dictionary, com.pwc.dictionary.Trie, com.pwc.dictionary.Node

####分词算法--正向最大匹配
当词典构建完毕，算法从语句中找出词典中存在的最长的词语。未消歧，但算法速度块，文本分类器中做特征抽取，分类效果不错。
*    算法的类：com.pwc.breaker.Breaker
