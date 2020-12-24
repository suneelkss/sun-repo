/*

 * Copyright 2004 The Apache Software Foundation.

 * 

 * Licensed under the Apache License, Version 2.0 (the "License");

 * you may not use this file except in compliance with the License.

 * You may obtain a copy of the License at

 * 

 *      http://www.apache.org/licenses/LICENSE-2.0

 * 

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 * See the License for the specific language governing permissions and

 * limitations under the License.

 */

package com.marta.admin.tags.aggregate;



import java.math.BigDecimal;

import com.marta.admin.tags.aggregate.AggregateFunction;



/**

 * Class that implements an average aggregate function.

 *

 * @see AggregateFunction

 */



public class AvgAggregateFunction implements AggregateFunction

{

  private double total;

  private long   count;

  

  public AvgAggregateFunction()

  {

    total = 0;

    count = 0;

  }



  public void addValue (Object obj)

  {

    if ((obj != null) && (obj instanceof Number))

    {

      total += ((Number) obj).doubleValue ();

      count++;

    }

  }



  public Object totalValue ()

  {

    double result = 0;



    if (count > 0) result = total / count;



    return (new Double (result));

  }

}

