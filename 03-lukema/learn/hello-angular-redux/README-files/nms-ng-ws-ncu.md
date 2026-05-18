# `nms-ng-ws` `ncu` Notes

## How To Fix `ncu` Exception

    # `ncu` Exception:
    #
    D:\02-LukeTools\node-v16.16.0-win-x64\node_modules\npm-check-updates\build\src\index.js:56
        throw err;
        ^
    
    TypeError: dep.includes is not a function
        at D:\02-LukeTools\node-v16.16.0-win-x64\node_modules\npm-check-updates\build\src\lib\getPreferredWildcard.js:22:134
        at Array.find (<anonymous>)

### 1. `packages.json`: Delete `bundleDependencies` Block

    "bundleDependencies": [
      "@nms/common",
      "@nms/core"
    ]

### 2. Run `ncu`, `ncu -u`

### 3. Add Back `bundleDependencies` Block

    "bundleDependencies": [
      "@nms/common",
      "@nms/core"
    ]

### 4. Run `npm i --force`

    num i --force
