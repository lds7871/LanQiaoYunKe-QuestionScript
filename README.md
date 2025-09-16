#蓝桥题库自动刷题脚本
程序仅供交流学习。
<h2>脚本流程架构</h2>
<img width="400" height="350" alt="image" src="https://github.com/user-attachments/assets/0a6436f6-3624-4b5b-a6e0-f0af14e65c5e" />
现在你每次运行都会产生日志文件“log+日期”
<h2>更新公告</h2>
<li>25/5/29————经过两小时多的测试，账号测试题目共470+道，测试通过301题。</li>
<li>25/9/16————优化了代码架构，增加了日志和记录功能，增强代码可读性，更新README。</li>
<h2>预先准备</h2>
  <li>下载Edgedriver程序————https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/?form=MA13LH（这是微软的浏览器自动驱动软件，只需要几十兆）</li>
  <li>请确保Edgedriver程序与你的Edge浏览器保持版本一致</li>
  <li>修改config.txt————按照config.txt内的要求覆盖替换为你的账号密码以及路径</li>
  <li>配置Maven———下载三方库</li>
<h2>仓库说明:</h2>
<li>仓库项目无不良引导，请自行甄别。本质原理就是读取网页内的已解决的代码进行复制粘贴然后检测。</li>
<li>推荐题目从100开始，请在网络环境较好的地方运行。不想运行直接关掉就行了，下次启动会继续上次的题目Id</li>
<li>本质一切无法解决的问题都会自动关闭浏览器并切换到下一题。可能原因：题目Id未找到（蓝桥题库Id确实是不连续的）/无已解决的答案/无可复制答案/复制的答案错误</li>

