package com.learn.str;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StringConcatAllCombo {

    public static void main(String[] args) {
        String s = "barfoothefoobarfooman";
        String[] words = { "foo", "bar" };

        // String s = "pjzkrkevzztxductzzxmxsvwjkxpvukmfjywwetvfnujhweiybwvvsrfequzkhossmootkmyxgjgfordrpapjuunmqnxxdrqrfgkrsjqbszgiqlcfnrpjlcwdrvbumtotzylshdvccdmsqoadfrpsvnwpizlwszrtyclhgilklydbmfhuywotjmktnwrfvizvnmfvvqfiokkdprznnnjycttprkxpuykhmpchiksyucbmtabiqkisgbhxngmhezrrqvayfsxauampdpxtafniiwfvdufhtwajrbkxtjzqjnfocdhekumttuqwovfjrgulhekcpjszyynadxhnttgmnxkduqmmyhzfnjhducesctufqbumxbamalqudeibljgbspeotkgvddcwgxidaiqcvgwykhbysjzlzfbupkqunuqtraxrlptivshhbihtsigtpipguhbhctcvubnhqipncyxfjebdnjyetnlnvmuxhzsdahkrscewabejifmxombiamxvauuitoltyymsarqcuuoezcbqpdaprxmsrickwpgwpsoplhugbikbkotzrtqkscekkgwjycfnvwfgdzogjzjvpcvixnsqsxacfwndzvrwrycwxrcismdhqapoojegggkocyrdtkzmiekhxoppctytvphjynrhtcvxcobxbcjjivtfjiwmduhzjokkbctweqtigwfhzorjlkpuuliaipbtfldinyetoybvugevwvhhhweejogrghllsouipabfafcxnhukcbtmxzshoyyufjhzadhrelweszbfgwpkzlwxkogyogutscvuhcllphshivnoteztpxsaoaacgxyaztuixhunrowzljqfqrahosheukhahhbiaxqzfmmwcjxountkevsvpbzjnilwpoermxrtlfroqoclexxisrdhvfsindffslyekrzwzqkpeocilatftymodgztjgybtyheqgcpwogdcjlnlesefgvimwbxcbzvaibspdjnrpqtyeilkcspknyylbwndvkffmzuriilxagyerjptbgeqgebiaqnvdubrtxibhvakcyotkfonmseszhczapxdlauexehhaireihxsplgdgmxfvaevrbadbwjbdrkfbbjjkgcztkcbwagtcnrtqryuqixtzhaakjlurnumzyovawrcjiwabuwretmdamfkxrgqgcdgbrdbnugzecbgyxxdqmisaqcyjkqrntxqmdrczxbebemcblftxplafnyoxqimkhcykwamvdsxjezkpgdpvopddptdfbprjustquhlazkjfluxrzopqdstulybnqvyknrchbphcarknnhhovweaqawdyxsqsqahkepluypwrzjegqtdoxfgzdkydeoxvrfhxusrujnmjzqrrlxglcmkiykldbiasnhrjbjekystzilrwkzhontwmehrfsrzfaqrbbxncphbzuuxeteshyrveamjsfiaharkcqxefghgceeixkdgkuboupxnwhnfigpkwnqdvzlydpidcljmflbccarbiegsmweklwngvygbqpescpeichmfidgsjmkvkofvkuehsmkkbocgejoiqcnafvuokelwuqsgkyoekaroptuvekfvmtxtqshcwsztkrzwrpabqrrhnlerxjojemcxel";
        // String[] words = { "dhvf", "sind", "ffsl", "yekr", "zwzq", "kpeo", "cila", "tfty", "modg", "ztjg", "ybty", "heqg", "cpwo", "gdcj", "lnle", "sefg", "vimw", "bxcb" };

        StringConcatAllCombo stringConcat = new StringConcatAllCombo();
        List<String> allCombos = stringConcat.allCombos(words);

        log.info("allCombos size: {}", allCombos.size());
        // log.info("allCombos: {}", allCombos);

        // Remove duplicates
        allCombos = allCombos.stream().distinct().collect(Collectors.toList());

        log.info("allCombos: {}", allCombos.size());

        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < allCombos.size(); i++) {

            String str = allCombos.get(i);
            int idx = s.indexOf(str);
            while (idx > -1) {
                positions.add(idx);
                idx = s.indexOf(str, idx + 1);
            }
        }

        log.info("positions: {}", () -> positions);
    }

    public List<String> allCombos(String[] words) {
        List<String> leadingWords = new ArrayList<>();
        return remainCombos(words, leadingWords);
    }

    public List<String> remainCombos(String[] words, List<String> leadingKeys) {
        if (words == null || words.length == 0) {
            return new ArrayList<>();
        }

        List<String> ret = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            List<String> currentKeys = new ArrayList<>(leadingKeys);
            String word = words[i];
            String key = word + i;
            if (currentKeys.contains(key)) {
                continue;
            } else {
                if (currentKeys.size() == words.length - 1) {
                    ret.add(word);
                } else {
                    currentKeys.add(key);
                    List<String> remain = remainCombos(words, currentKeys);
                    remain.forEach(e -> {
                        ret.add(word + e);
                    });
                }
            }
        }

        return ret;
    }

    /*
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> list = new ArrayList<>();
    
        int len = words[0].length();
    
        List<String> usedWords = new ArrayList<>();
        this.buildList(s, 0, list, words, usedWords, len);
    
        return list;
    }
    
    void buildList(String s, int sPos, List<Integer> list, String[] words, List<String> usedWords, int len) {
        if (sPos < 0 || sPos >= s.length() - len || usedWords.size() == words.length) {
            return;
        }
    
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            String key = w + i;
            if (usedWords.contains(key)) {
                continue;
            } else {
                int pos = s.indexOf(w, sPos + usedWords.size() * len);
    
                if ((usedWords.size() == 0 && pos > -1) || (usedWords.size() > 0 && pos == 0)) {
                    if (usedWords.size() == words.length) {
                        list.add(s.indexOf(usedWords.get(0)));
                        usedWords.clear();
                        return;
                    } else {
                        usedWords.add(key);
                        this.buildList(s, sPos + pos + len, list, words, usedWords, len);
                    }
                } else {
                    usedWords.clear();
                    return;
                }
            }
        }
    }
    */
}
