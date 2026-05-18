// Import the cucumber operators we need
import { DataTable, Given, Then, When } from '@cucumber/cucumber';
import { expect } from 'chai';
import { UserLogin } from './user-login.po';

const page = new UserLogin();
let fullname: string;

Given('I open browser with url', async (dataTable: DataTable) => {
    const userData = dataTable.hashes();
    const url = userData[0].url;

    console.log('url', url);

    await page.navigateTo(url);
});

Given('I enter fullname and password', async (dataTable: DataTable) => {
    const userData = dataTable.hashes();
    fullname = userData[0].fullname;

    console.log('fullname', fullname);

    const password = userData[0].password;

    await page.setFullname(fullname);
    await page.setPassword(password);
});

When('I click login button', async () => {
    await page.sendEnterKey();
});

Then('I can verify my login', async () => {
    const text = await page.getPageSource();

    expect(text).to.include('Hello');
    expect(text).to.include(fullname);

    /**
     * make the call block and wait.
     */
    await page.closeAndQuit();
});

