# Optimization techniques on implementations of irregular data-structures (graphs) #
| Exploring pointer-based graph structures (i.e., irregular data-structures) has been enphasized by the scientific community that aims to use such structures on several specific areas. Many of these areas (with more or less applicability in a wide range of concepts in science and engineering) have the need to implement complex and, sometimes, extremely sophisticated algorithms, like finding the Minimum Spanning Tree (MST) of a graph - assuming such graph is connected and undirected. The objective is optimizing the implementation of graph structures aiming for better memory locality. Memory issues have been attenuated by using increasingly faster cache memories in nowadays computer architectures - the memory optimization techniques for regular data-structures (eg., matrices) are well known, as for irregular data-structures (eg., graphs) insert themselves in the gray areas of efficient representations. Memory issues on graph representation are studied, and effort is made to alert to the need of more efficiency on complex and irregular data-structures in computer architectures. |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Important recent updates ##
  * New API based on the study of JUNG and JGraph libraries
  * wiki: [Graph Restructuring](http://code.google.com/p/ugl-uminho-cpd/wiki/Phase2_RestructuringAPI)
  * new benchmarks available on previous link
  * [myNewLib\_Implementation](http://code.google.com/p/ugl-uminho-cpd/wiki/myNewLib_Implementation): class differences between JUNG, JGraph and myNewLib
  * new idea to work on: [UndirectedNeighborsCachingGraph](http://code.google.com/p/ugl-uminho-cpd/issues/detail?id=7) and [Prefetching and Iterators](http://code.google.com/p/ugl-uminho-cpd/issues/detail?id=9)


## Next steps ##
  * do intensive profiling
  * refining graph structure implementations
  * possibly implement auxiliary mechanisms like edge ordering, graph partitioning, etc.
  * expand graph API for new features

_Nuno Faria_, Universidade do Minho