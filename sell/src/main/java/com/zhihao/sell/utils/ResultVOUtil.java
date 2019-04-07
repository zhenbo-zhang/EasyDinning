package com.zhihao.sell.utils;

import com.zhihao.sell.VO.ResultVO;

/**
 * An util class which used to return ResultVO object to front end.
 */
public class ResultVOUtil {

  /**
   * Return the ResultVO if succeed.
   *
   * @param object - the object to return
   * @return the ResultVO
   */
  public static ResultVO success(Object object) {
    ResultVO resultVO = new ResultVO();
    resultVO.setData(object);
    resultVO.setCode(0);
    resultVO.setMsg("Success");
    return resultVO;
  }

  /**
   * Return the ResultVO if succeed.
   *
   * @return the ResultVO
   */
  public static ResultVO success() {
    return success(null);
  }

  /**
   * Return the ResultVO if fail.
   *
   * @param code - the code to return
   * @param msg - the error message to return
   * @return the ResultVO
   */
  public static ResultVO error(Integer code, String msg) {
    ResultVO resultVO = new ResultVO();
    resultVO.setCode(code);
    resultVO.setMsg(msg);
    return resultVO;
  }
}
