# vscode settings

## How do I opt out of VS Code auto-updates globally?

    # option 1:
    (Left-bottom Gear) Settings (Manage):
    # or
    File -> Preferences -> Settings
    
    search "update mode" and change the setting to none.

    # option 2:
    (windows) C:\Users\lma\AppData\Roaming\Code\User.settings.json:
    (linux) ~/.config/Code/User/settings.json:

    # vscode version update:
    "update.mode": "none"

    # extension update:
    "extensions.autoUpdate": false
