# Angular versions

## Cheatsheet

[Cheatsheet]<https://gist.github.com/jonlabelle/706b28d50ba75bf81d40782aa3c84b3e>

    {
      "dependencies": {
        "express": "^4.18.0", // Allow minor updates: 4.18.x, 4.19.x
        "lodash": "~4.17.21", // Allow patch updates: 4.17.x only
        "react": ">=18.0.0", // Any version 18.0.0 or higher
        "typescript": "4.x", // Any version in the 4.x series
        "eslint": "*", // Always get the latest version
        "axios": ">=1.0.0 && <2.0.0", // Must be 1.x series only
        "my-utils": "workspace:^1.0.0", // Workspace dependency
        "local-pkg": "file:../local-package" // Local file dependency
      },
      "devDependencies": {
        "jest": "29.0.0 - 29.5.0", // Specific range
        "prettier": "2.8.8", // Exact version
        "@types/node": ">=16.0.0 <21.0.0" // Space-separated AND
      }
    }
