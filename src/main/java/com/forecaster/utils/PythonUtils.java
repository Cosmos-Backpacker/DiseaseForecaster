package com.forecaster.utils;

import com.forecaster.pojo.Result;
import com.forecaster.pojo.Userheart;
import com.forecaster.service.IUserheartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@Slf4j
public class PythonUtils {

    //用一个变量count来记录医疗机器人的请求次数，第一次请求时需要初始化耗时较长
    //后面就请求另一个文件就可以不用初始化了
    public static int count = 0;


    @Autowired
    private IUserheartService userheartService;

    //心脏病算法调用
    public Result userheartAlgorithm(Integer uid, Integer index) {

        StringBuilder result = new StringBuilder();      //用于收集组装返回的结果对象

        log.info("正在调用python算法");
        // python脚本的绝对路径，在windows中用"\\"分隔，在Linux中用"/"分隔
        String pyPath = "E:\\demo.exe";

        //组装数据data=
        //先根据选择从数据库获取数据
        Userheart userheart = userheartService.getByIndex(uid, index);
        //System.out.println(userheart.dataToString());
        String data = userheart.dataToString();
        // 传入python脚本的参数为”111“
        String[] args1 = new String[]{pyPath, data};


        try {
            // 执行Python文件，并传入参数
            Process process = Runtime.getRuntime().exec(args1);
            // 获取Python输出字符串作为输入流被Java读取
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //原作者此处只能读取并输出一行信息
//           String actionStr = in.readLine();//此处只能读取一行数据
//            if (actionStr != null) {
//                System.out.println(actionStr);
//            }
            //经过改变之后可以将python中所有的输出内容输出出来
            String actionStr;
            while ((actionStr = in.readLine()) != null) {
//                System.out.println("1111");
                System.out.println(actionStr);
                result.append("\n" + actionStr);      //组装结果
            }

            in.close();
            process.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("代码调用成功");
        System.out.println(result);
        return Result.success("算法计算成功", result);
    }


    //调用查询python代码
    public Result pythonQuestion(String question) {
        StringBuilder result_jieshao = new StringBuilder();//收集疾病介绍的结果
        StringBuilder result_zhengzhuang = new StringBuilder();//收集疾病症状的结果
        StringBuilder result_bingyin = new StringBuilder();//收集疾病病因的结果
        StringBuilder result_bingfazheng = new StringBuilder();//收集疾病并发症
        StringBuilder result_dofood = new StringBuilder();//收集疾病该吃什么的结果
        StringBuilder result_not_food = new StringBuilder();//收集疾病不能吃什么的结果
        StringBuilder result_drug = new StringBuilder();//收集疾病用药的结果
        StringBuilder result_yufang = new StringBuilder();//收集疾病如何预防的结果
        StringBuilder result_zhiliao = new StringBuilder();//收集疾病如何治疗的结果
        StringBuilder result_zhiliao_long = new StringBuilder();//收集疾病治疗多久的结果
        StringBuilder result_zhiliao_gailv = new StringBuilder();//收集疾病治疗概率的结果
        StringBuilder result_renqun = new StringBuilder();//收集疾病易发人群的结果
        StringBuilder result_jiancha = new StringBuilder();//收集疾病检查项目的结果
        //用于封装结果
        HashMap<String, String> map = new HashMap<String, String>();


        log.info("正在调用python算法");
        // python脚本的绝对路径，在windows中用"\\"分隔，在Linux中用"/"分隔
        String pyPath = "D:\\PythonProject\\kbmedical\\KBMEDICAL\\chatbot_graph.py";

        //组装数据data=
        //先根据选择从数据库获取数据
        //System.out.println(userheart.dataToString());
        // 传入python脚本的参数为”111“
        String[] args1 = new String[]{"python", pyPath, question};

        try {
            // 执行Python文件，并传入参数
            Process process = Runtime.getRuntime().exec(args1);

            // 获取Python输出字符串作为输入流被Java读取
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //原作者此处只能读取并输出一行信息
//           String actionStr = in.readLine();//此处只能读取一行数据
//            if (actionStr != null) {
//                System.out.println(actionStr);
//            }
            //经过改变之后可以将python中所有的输出内容输出出来

            //System.out.println(in.readLine());
            //log.info("调用前时间为{}", LocalDateTime.now());
            String question_back;//用于不断扫描信号
            //获取疾病介绍的内容
            while ((question_back = in.readLine()) != null) {
                //System.out.println(question_back);
                if ((question_back).contains("是什么病")) {
                    String jieshao;
                    while ((jieshao = in.readLine()) != null) {
                        if (jieshao.contains("end")) {
                            break;
                        } else {
                            result_jieshao.append(jieshao);
                        }
                    }
                }

                if ((question_back).contains("症状是什么")) {
                    String zhengzhuang;
                    while ((zhengzhuang = in.readLine()) != null) {
                        if (zhengzhuang.contains("end")) {
                            break;
                        } else {
                            result_zhengzhuang.append(zhengzhuang);
                        }
                    }
                }

                if ((question_back).contains("病因是什么")) {
                    String bingyin;
                    while ((bingyin = in.readLine()) != null) {
                        if (bingyin.contains("end")) {
                            break;
                        } else {
                            result_bingyin.append(bingyin);
                        }
                    }
                }

                if ((question_back).contains("并发症有哪些")) {
                    String bingfazheng;
                    while ((bingfazheng = in.readLine()) != null) {
                        if (bingfazheng.contains("end")) {
                            break;
                        } else {
                            result_bingfazheng.append(bingfazheng);
                        }
                    }
                }

                if ((question_back).contains("该吃什么")) {
                    String food;
                    while ((food = in.readLine()) != null) {
                        if (food.contains("end")) {
                            break;
                        } else {
                            result_dofood.append(food);
                        }
                    }
                }

                if ((question_back).contains("不能吃什么")) {
                    String food;
                    //System.out.println("测试1");
                    while ((food = in.readLine()) != null) {
                        //System.out.println("没有读到内容");
                        if (food.contains("end")) {
                            //System.out.println("读到了end");
                            break;
                        } else {
                            //System.out.println("sdlfajsdlkf");
                            result_not_food.append(food);
                        }
                    }
                }

                if ((question_back).contains("该用什么药")) {
                    String drug;
                    while ((drug = in.readLine()) != null) {
                        if (drug.contains("end")) {
                            break;
                        } else {
                            result_drug.append(drug);
                        }
                    }
                }


                if ((question_back).contains("如何预防")) {
                    String yufang;
                    while ((yufang = in.readLine()) != null) {
                        if (yufang.contains("end")) {
                            break;
                        } else {
                            result_yufang.append(yufang);
                        }
                    }
                }

                if ((question_back).contains("怎么治疗")) {
                    String zhiliao;
                    while ((zhiliao = in.readLine()) != null) {
                        if (zhiliao.contains("end")) {
                            break;
                        } else {
                            result_zhiliao.append(zhiliao);
                        }
                    }
                }

                if ((question_back).contains("治疗多久")) {
                    String zhiliao_long;
                    while ((zhiliao_long = in.readLine()) != null) {
                        if (zhiliao_long.contains("end")) {
                            break;
                        } else {
                            result_zhiliao_long.append(zhiliao_long);
                        }
                    }
                }

                if ((question_back).contains("多大概率")) {
                    String zhiliao_gailv;
                    while ((zhiliao_gailv = in.readLine()) != null) {
                        if (zhiliao_gailv.contains("end")) {
                            break;
                        } else {
                            result_zhiliao_gailv.append(zhiliao_gailv);
                        }
                    }
                }

                if ((question_back).contains("易发人群")) {
                    String renqun;
                    while ((renqun = in.readLine()) != null) {
                        if (renqun.contains("end")) {
                            break;
                        } else {
                            result_renqun.append(renqun);
                        }
                    }
                }

                if ((question_back).contains("检查项目")) {
                    String jiancha;
                    while ((jiancha = in.readLine()) != null) {
                        if (jiancha.contains("end")) {
                            break;
                        } else {
                            result_jiancha.append(jiancha);
                        }
                    }
                }
            }

            //全部结果查询完后开始组装
            map.put("介绍", String.valueOf(result_jieshao));
            map.put("症状", String.valueOf(result_zhengzhuang));
            map.put("病因", String.valueOf(result_bingyin));
            map.put("并发症", String.valueOf(result_bingfazheng));
            map.put("推荐食物", String.valueOf(result_dofood));
            map.put("忌食", String.valueOf(result_not_food));
            map.put("用药", String.valueOf(result_drug));
            map.put("预防", String.valueOf(result_yufang));
            map.put("如何治疗", String.valueOf(result_zhiliao));
            map.put("治疗时间", String.valueOf(result_zhiliao_long));
            map.put("治好概率", String.valueOf(result_zhiliao_gailv));
            map.put("易发病人群", String.valueOf(result_renqun));
            map.put("需要检查项目", String.valueOf(result_jiancha));


            //log.info("调用后时间为{}", LocalDateTime.now());
            in.close();
            process.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("代码调用成功");

        return Result.success("查询成功", map);

    }

}
