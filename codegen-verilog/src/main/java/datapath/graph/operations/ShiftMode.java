package datapath.graph.operations;

/**
 * This enum specifies the type of shift operation - logical left,
 * arithmetic right, etc.
 * <p>
 * $Id$
 * <p>
 * Copyright 2008 by the
 * <a href="http://ali-www.cs.umass.edu/">Scale Compiler Group</a>,<br>
 * <a href="http://www.cs.umass.edu/">Department of Computer Science</a><br>
 * <a href="http://www.umass.edu/">University of Massachusetts</a>,<br>
 * Amherst MA. 01003, USA<br>
 * All Rights Reserved.<br>
 */
public enum ShiftMode
{
  /**
   * The shift is to the righ with sign extension.
   */
 SignedRight(),
  /**
   * The shift is to the righ with zero bit fill on the left.
   */
 UnsignedRight(),
  /**
   * The shift is to the left with zero bit fill on the right.
   */
 Left(),
  /**
   * The shift is to the left with bits shifted off the left side
   * inserted on the right.
   */
 LeftRotate(),
  /**
   * The shift is to the right with bits shifted off the right side
   * inserted on the left.
   */
 RightRotate(),
 /**
  * The is to the right and if the shift is signed or unsigned is defined by the
  * type of the operation
  */
 Right();
}
