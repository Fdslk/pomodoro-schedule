# pomodprp-schedule
Intellig plugin for time management

## some concept
* plugins.xml
* Action
  * definition, is the response of the interaction of user's operations
  and all the actions need to be defined and registered in IntelliJ platform
  * how to
    * define a custom action which needs to extend AnAction who is an abstract class
    * override ```actionPerformed``` method, this method will be triggered when user invokes some actions
    * override ```update``` method, this method will use to enable the action and the default is enabled
    * register custom action in ```plugin.xml```
  * how to group actions
    * add to a existing group
      * add tab \<group>
      * bind action to group by using tab \<add-to-group> with the property ```relative-to-action```
    * create a new group
      * Extending ```DefaultActionGroup```
      * register new group
  * how to do data persistence
    * the persistence data class is separated with the settings
    * combine the settings into one class
      ```kotlin
      public void loadState(MyService state) {
        XmlSerializerUtil.copyBean(state, this);
      }
      ```
  * how to show UI
    * how to show input
    * how to upload file
    * tips
      * If your project is built by **Gradle**, GUI Designer will not generate java source code.
      * you can generate sources code as following: [using idea to build code](https://www.jetbrains.com/help/idea/work-with-gradle-projects.html#delegate_build_actions)
  * how to show dialog 
    * go to your src folder->New->Swing GUI Designer->input required info->create
    * if the UI related java code could not generate automatically, you can check the following
    setting
    ![GUI Designer](https://user-images.githubusercontent.com/6279298/184563693-fbf2e41e-0dc1-471b-83dd-4a555d111f2d.png)
  * how to show PopupList
