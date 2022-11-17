# polyv-competition
## 基础配置

主要参考保利威android SDK快速集成可能遇到问题 [快速集成](https://help.polyv.net/index.html#/live/android/2-%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90)
<br>
### 1.报错 `Using insecure protocols with repositories, without explicit opt-in, is unsupported. Sw....`<br>
#### &emsp;&emsp;&emsp;意思是不安全协议<br>
### 解决方法<br>
```
maven {
              allowInsecureProtocol = true//加上这一句
              url 'http://maven.aliyun.com/nexus/content/repositories/releases/' 
              }
```
### 2.报错 `Build was configured to prefer settings repositories over project repositories but repository....` <br>
#### &emsp;&emsp;&emsp;仓库冲突？<br>
### 解决方法<br>
&emsp;&emsp;把setting.gradle中的`dependencyResolutionManagement`注释掉
```
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
```
### 3. 项目的`build.gradle`中`repositories`还要加上这两句代码，匪夷所思，有无董哥，不然还会报错<br>
```
        //不知道为什么加上下面这两个url就正常了
        maven {
            allowInsecureProtocol = true
            url 'http://maven.aliyun.com/nexus/content/repositories/google' }
        maven {
            allowInsecureProtocol = true
            url 'http://maven.aliyun.com/nexus/content/repositories/jcenter'}
```
### 3. 项目的`build.gradle`中`repositories`还要加上这两句代码，匪夷所思，有无董哥，不然还会报错<br>
```
        //不知道为什么加上下面这两个url就正常了
        maven {
            allowInsecureProtocol = true
            url 'http://maven.aliyun.com/nexus/content/repositories/google' }
        maven {
            allowInsecureProtocol = true
            url 'http://maven.aliyun.com/nexus/content/repositories/jcenter'}
```
### 4. 项目的`gradle.properties`还要加上一句`android.enableJetifier=true` 适配androidx?<br>
### 5. 官方demo标红的原因估计是旧版本不适配androidx,拉取官方demo中的androidx分支可以解决[链接](https://gitee.com/polyv_ef/polyv-android-livescenes-sdk-demo#https://gitee.com/link)<br>
&emsp;&emsp; 打死我也不想配环境了
