[![Java CI with Gradle](https://github.com/Fdslk/pomodoro-schedule/actions/workflows/gradle.yml/badge.svg)](https://github.com/Fdslk/pomodoro-schedule/actions/workflows/gradle.yml)
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
  * how to add widget
    * add a widget factory then register it
      * ```<statusBarWidgetFactory implementation="pomodoro.widget.PomodoroWidgetFactory"/>```
  * how to release a plugin in JetBrains MarketSpace
    * using gradle sign
    * using CTL
      * package your code into `zip file`, running cmd `./gradlew build`, then you can find your plugin in the directory `./build/distributions`
      * try plugin in your local machine
      ![install plugin to your local machine](https://user-images.githubusercontent.com/6279298/199129178-ce977fc8-fc52-4a14-bca5-9da33b1fadc7.png)
      * If the following error occurs to u, 
        * ```
          Plugin 'Kotlin' (version '211-1.5.20-release-284-IJ7442.40') is not compatible with the current version of the IDE, because it requires build 211.* or older but the current build is IU-212.4746.92
          ```
        * you might set specific IDE build version in `build.gradle` and add build start version in `plugin.xml` like
        ```xml
          version.set(
            System.getenv().getOrDefault(
                    "IJ_VERSION",
                    "213.7172.25"
            )
          )
        ```
        ```json
            <idea-version since-build="213.7172.25"/>
        ```
      * If everything is ok at here
        * generate private key
      ```cmd
       openssl genpkey\
         -aes-256-cbc\
         -algorithm RSA\
         -out private.pem\
         -pkeyopt rsa_keygen_bits:4096
      ```
      * generate certificate
      ```cmd
       openssl req\
         -key private.pem\
         -new\
         -x509\
         -days 365\
         -out chain.crt
      ``` 
      * sign your plugin zip. Download the latest [marketplace-zip-client](https://github.com/JetBrains/marketplace-zip-signer/releases)
      If the password includes some special characters, you need to add a `escape character` before it.
        ```
        java -jar marketplace-zip-signer-cli.jar sign\
          -in "unsigned.zip"\
          -out "signed.zip"\
          -cert-file "/path/to/chain.crt"\
          -key-file "/path/to/private.pem"\
          -key-pass "PRIVATE_KEY_PASSWORD"
        ```
      * create jetBrains account, here I use google account. upload your plugin and wait two business days. Market will send you the verification result.
