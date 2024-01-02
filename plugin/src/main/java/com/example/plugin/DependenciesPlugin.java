package com.example.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.List;

class DependenciesPlugin implements Plugin<Project> {

    private final String TAG = "DependenciesPlugin >>>>> ";

    @Override
    public void apply(Project project) {
        System.out.println(TAG + this.getClass().getName());

        Extension extension = project.getExtensions().create("printDependencies", Extension.class);

        project.afterEvaluate(pro -> {
            System.out.println(TAG + "log print status:" + extension.getEnable().get());

            //开启日志打印
            if (extension.getEnable().get()) {

                // 创建两个新的Configuration，分别用于官方库和三方库
                List<String> officialLibs = new ArrayList<>();
                List<String> thirdPartyLibs = new ArrayList<>();


                // 遍历所有已有的依赖，并根据groupId将其分类到对应的配置中
                project.getConfigurations().all(configuration -> {
                    if (configuration.getName().equals("implementation")) { // 或者是其他你想要处理的依赖类型
                        configuration.getDependencies().all(dependency -> {

                            String groupId = dependency.getGroup();
                            if (groupId == null) {
                                groupId = "";
                            }
                            if (groupId.contains("androidx") || groupId.contains("com.google") ||
                                    groupId.contains("org.jetbrains")) {
                                officialLibs.add(groupId);
                            } else {
                                thirdPartyLibs.add(groupId);
                            }
                        });
                    }
                });

                System.out.println("--------------officialLibs start--------------");
                officialLibs.forEach(System.out::println);
                System.out.println("--------------officialLibs end--------------");

                System.out.println("--------------thirdPartyLibs start--------------");
                thirdPartyLibs.forEach(System.out::println);
                System.out.println("--------------thirdPartyLibs end--------------");


            } else {
                System.out.println("disable log print");
            }
        });
    }
}

