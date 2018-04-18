package com.suramire.myapplication.model;

//实体类-首页版本更新内容


public class Update {
    /**
     * versionCode : 405010
     * versionName : 4.5.01
     * fullPath : http://fd.hhz360.com/static/ra/updateApp/raForUpdate.apk
     * appName : raForUpdate.apk
     * updateInfo : <li>支持租金日期绑定智能锁有效期. 提示：自动升级失败后，卸载当前版本，然后到手机的download 文件夹下，安装 raForUpdate.apk</li>
     * mustUpdate : false
     * versionCodeIOS : 40310
     * versionNameIOS : 4.3.10
     * fullPathIOS : https://itunes.apple.com/cn/app/hao-hao-zu-fang-dong-ban-fang/id968268081?mt=8
     * updateInfoIOS : <li>支持智能水表</li><li>支持分散式抄表和智能抄表</li><li>退租新增重新计算功能</li><li>优化智能门锁功能</li><li>其他功能优化</li><li style=color: red>（请到AppStore更新）</li>
     * mustUpdateIOS : true
     */

    private int versionCode;
    private String versionName;
    private String fullPath;
    private String appName;
    private String updateInfo;
    private boolean mustUpdate;
    private int versionCodeIOS;
    private String versionNameIOS;
    private String fullPathIOS;
    private String updateInfoIOS;
    private boolean mustUpdateIOS;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public boolean isMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public int getVersionCodeIOS() {
        return versionCodeIOS;
    }

    public void setVersionCodeIOS(int versionCodeIOS) {
        this.versionCodeIOS = versionCodeIOS;
    }

    public String getVersionNameIOS() {
        return versionNameIOS;
    }

    public void setVersionNameIOS(String versionNameIOS) {
        this.versionNameIOS = versionNameIOS;
    }

    public String getFullPathIOS() {
        return fullPathIOS;
    }

    public void setFullPathIOS(String fullPathIOS) {
        this.fullPathIOS = fullPathIOS;
    }

    public String getUpdateInfoIOS() {
        return updateInfoIOS;
    }

    public void setUpdateInfoIOS(String updateInfoIOS) {
        this.updateInfoIOS = updateInfoIOS;
    }

    public boolean isMustUpdateIOS() {
        return mustUpdateIOS;
    }

    public void setMustUpdateIOS(boolean mustUpdateIOS) {
        this.mustUpdateIOS = mustUpdateIOS;
    }
}
