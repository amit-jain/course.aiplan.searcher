course.aiplan.searcher
======================

A generic search component for AI Planning. This project has been created as submission for the Creative Challenge assignment for the coursera AI Planning course - www.coursera.org/course/aiplan. 
The library currently implements the following algorithms :
1. BFS
2. DFS
3. UCS
4. ASTAR
5. ASTAR Graph

More will be added subsequently. Any client application will need to inherit from the Node interface and call the SearchExecutor class with the appropriate algorithm type (Defined in SearchType Enum) and the initial state.
