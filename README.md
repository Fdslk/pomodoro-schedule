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
  * how to show UI
    * how to show input
    * how to upload file
  * how to show dialog
  * how to show PopupList
