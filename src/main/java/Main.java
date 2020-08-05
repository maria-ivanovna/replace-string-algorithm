import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "aabaabaabaabbaaabaabaab";
        String replaceMe = "aabaab";
        String replacWith = "cck";

        ChangeMaster changeMaster = new ChangeMaster();

        System.out.println(changeMaster.uniquePositionSearch(input.toCharArray(), replaceMe.toCharArray()));

        System.out.println(changeMaster.betterPositionSearch(input.toCharArray(),
                replaceMe.toCharArray()));

        System.out.println(changeMaster.replaceUniqueAlg(input, replaceMe.toCharArray(), replacWith.toCharArray()));
    }

}
class ChangeMaster{

    //Поиск позиций тех значений что не накладываються друг на друга
    public ArrayList<Integer> uniquePositionSearch(char []input, char []repMe){
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for(int i = 0; i<input.length; i++){
            int count = 0;
            while (count<repMe.length && i+count < input.length && repMe[count] == input[i + count]){
                count++;
            }
            if(count == repMe.length){
                positions.add(i);
                i = i+repMe.length - 1;
            }

        }
        return positions;
    }

    //Поиск позиций aлгоритмом Кнута-Морриса-Пратта (значения могут накладываться друг на друга)
    public ArrayList<Integer> betterPositionSearch(char []input, char []repMe){
        ArrayList<Integer> positions = new ArrayList<Integer>();
        int[] preprefixFunction = prefixFunction(repMe);
        int i = 0;
        int count = 0;
        while (i<input.length){
            if(repMe[count] == input[i]){
                i++;
                count++;
            }
            if(count == repMe.length){
                positions.add(i - count);
                count = preprefixFunction[count - 1];
            }else if(i<input.length && repMe[count] != input[i]){
                if(count != 0){
                    count = preprefixFunction[count - 1];
                }else {
                    i = i + 1;
                }
            }
        }
        return positions;
    }

    public int[] prefixFunction(char []repMe){
        int []val = new int [repMe.length];
        for(int i = 1; i<repMe.length; i++){
            int count = 0;
            while (i+count<repMe.length && repMe[count] == repMe[i+count]){
                val[i+count] = Math.max(val[i+count], count+1);
                count++;
            }
        }
        return val;
    }


    //Бля замены подстроки был выбран алгоритм поиска уникальных значений (без накладывания значений друг на друга)
    //Так как рассматривался вариант замены слова на слово
    //В другом случае можно заменить метод uniquePositionSearch на betterPositionSearch для замены значений что накладываються друг на друга
    
    //Создать алгоритм без использования встомагательного функцианала не удалось...
    //Для преопределения строк был использован StringBuilder, так как алгоритм разрабатывался для слов замены произвольной длины (не обязательно одинаковой)
    //Иначе возникал ряд проблем с пересчётом и переопределением массива, что приводило к некоректой работе алгоритма
  
         

    public String replaceUniqueAlg(String input, char[] replaceMe, char[] replaceWith){
        List<Integer> positions = uniquePositionSearch(input.toCharArray(), replaceMe);
        String stringBuff = new String();
        for(Integer position : positions){
            StringBuilder result = new StringBuilder("");
            for(int i = 0; i<input.length(); i++){
                if(position == i){
                    result.append(replaceWith);
                    i = i + replaceMe.length - 1;
                }else {
                    result.append(input.charAt(i));
                }
            }
            input = result.toString();
        }
        return input;
    }
}
