# jaakrecog-fingerprint

This plugin is for jaakrecog fingerprint  
Current version `v.1.0.0 beta.1` 

## Versions
| Type               | Description |
|--------------------|-------------|
| `master` | Control of versions 1.0.0|
| `staging`          | prerelease: beta is for plublic use (v.1.0.0.dev.1) |
| `develop`       | prerelease: dev is for internal develop (v.1.0.0.beta.1) |

## Install

```bash
npm install jaakrecog-fingerprint
npx cap sync
```
## Usage
Import the library into the component where it needs to be deployed
```bash

import { FingerPrint } from 'jaakrecog-fingerprint';
```
Use the function to make auths
```bash
  async testAuth() {
    const result = await FingerPrint.echo(options);
    console.log(result);
  }
```
## API

<docgen-index></docgen-index>

<docgen-api>
<!-- run docgen to generate docs from the source -->
<!-- More info: https://github.com/ionic-team/capacitor-docgen -->
</docgen-api>
