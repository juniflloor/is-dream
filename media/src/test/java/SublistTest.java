import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/7 13:22
 */
public class SublistTest {

    public static void main(String [] args){

        Map<String, List<Integer>> myMap = new HashMap<>();
        List<Integer> myList = new ArrayList<>();
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        myList.add(6);

        myMap.put("my",myList);

        for(int i = 0; i < 6; i++) {

            List<Integer> tempList = myMap.get("my").subList(0,1);
            System.out.println(tempList.get(0));
        }
    }

}
