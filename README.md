# `rtn-local-authentication`

`rtn-local-authentication` is a local authentication library for React Native, built using Turbo Modules. It provides an interface for biometric and PIN authentication on Android devices.

## Features

- Support for fingerprint, PIN, and pattern authentication on Android.
- Fully compatible with React Native's Turbo Module system.
- Simple API to integrate local authentication into your React Native application.

## Installation

### Prerequisites

Ensure your React Native project is properly configured to use Turbo Modules. For more details, follow the official [React Native Turbo Modules documentation](https://reactnative.dev/docs/the-new-architecture/landing-page).

> If your version of react-native does not support turbo-modules, I recommend https://github.com/tradle/react-native-local-auth

### Install the package

```bash
npm install @carlossts/rtn-local-authentication
or
yarn add @carlossts/rtn-local-authentication
```

## UI

### authenticate method

<table>
  <tr>
<td><img src="https://res.cloudinary.com/dbw8igay3/image/upload/v1767028349/image01_hia3w7.png" alt="fingerprintOrPin" width="360" /></td>
<td><img src="https://res.cloudinary.com/dbw8igay3/image/upload/v1767028347/image02_pqry7x.png" alt="PIN" width="360" /></td>
<td><img src="https://res.cloudinary.com/dbw8igay3/image/upload/v1767028345/image03_yraxmp.png" alt="pattern" width="360" /></td>
</tr>
</table>

### isDeviceSecure method

<table>
  <tr>
<td><img src="https://res.cloudinary.com/dbw8igay3/image/upload/v1767028344/image04_shvpgp.png" alt="isDeviceSecure" width="360" /></td>
</tr>
</table>

## API Reference

## Methods

### `authenticate(map: { reason?: string; description?: string }): Promise<string>()`

Local device authentication process (using password, PIN, pattern or fingerprint), verifying that the device is protected by some security method, such as a password or biometrics.

Observation: If the device does not have local authentication, return success with the code WITHOUT_AUTHENTICATION.

## Options

| Option                | Description                                       |
| --------------------- | ------------------------------------------------- |
| reason                | Action title                                      |
| description           | Action description                                |


## Usage

```js
import React, { useCallback, useEffect } from 'react';
import { Alert, View } from 'react-native';
import {RNTLocalAuthentication} from '@carlossts/rtn-local-authentication';

const App = () => {
  const authenticationLocal = useCallback(async () => {
    try {
      await RNTLocalAuthentication?.authenticate({
        reason: 'Please authenticate yourself',
        description: 'Enter your password or fingerprint',
      });
    } catch (error) {
      Alert.alert('Authentication Failed', error.message);
    }
  }, []);

  useEffect(() => {
    authenticationLocal();
  }, [authenticationLocal]);

  return <View />;
};

export default App;
```

## ErrorCode

| Code                  | Description                                       |
| --------------------- | ------------------------------------------------- |
| E_AUTH_CANCELLED      | User canceled the authentication                  |
| E_ONE_REQ_AT_A_TIME   | Authentication already in progress                |
| E_FAILED_TO_SHOW_AUTH | Failed to create authentication intent            |

##

### `isDeviceSecure(): Promise<boolean>`

Checks if the device has some type of authentication.

## Usage

```js
import React, { useCallback, useEffect } from 'react';
import { Alert, View } from 'react-native';
import {RNTLocalAuthentication} from '@carlossts/rtn-local-authentication';

const App = () => {
  const isDeviceSecure = useCallback(async () => {
    try {
     const result = await RNTLocalAuthentication?.isDeviceSecure();
    Alert.alert('Is it a secure device ?', result ? 'Yes' : 'No');
    } catch (error) {
      Alert.alert('isDeviceSecure Failed', error.message);
    }
  }, []);

  useEffect(() => {
    isDeviceSecure();
  }, [isDeviceSecure]);

  return <View />;
};

export default App;
```

## License

[MIT](LICENSE.md)

