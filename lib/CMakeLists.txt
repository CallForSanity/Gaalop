OPTION(BUILD_WITH_OPENGL "build OpenGL visualization backend" OFF)

INCLUDE_DIRECTORIES("../include")

ADD_LIBRARY(gpc-base
GPCUtils.cpp
BaseVisualizationBackend.cpp)
INSTALL(TARGETS gpc-base DESTINATION lib)

IF(BUILD_WITH_OPENGL)
	FIND_PACKAGE(OpenGL)
	#FIND_PACKAGE(FreeGLUT) # free GLUT for drawing spheres, etc.
	# what is the best geometry drawing library out there?

	INCLUDE_DIRECTORIES(${OpenGL_INCLUDE_DIRS})
	ADD_LIBRARY(gpc-vis-opengl
				OpenGLVisualizationBackend.cpp)
	TARGET_LINK_LIBRARIES(gpc-vis-opengl ${OpenGL_LIBRARIES})
	INSTALL(TARGETS gpc-vis-opengl DESTINATION lib)
ENDIF(BUILD_WITH_OPENGL)

# TODO more backends
# DirectX
# OpenSceneGraph
# OGRE