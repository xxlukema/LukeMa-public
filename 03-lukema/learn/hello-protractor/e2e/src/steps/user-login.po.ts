import { browser, by, element, ElementFinder, ExpectedConditions } from 'protractor';

const until = ExpectedConditions;

export class UserLogin {

    /**
     * Reference code for ElementFinder if an element is referenced in multiple places.
     */
    // fullname: ElementFinder;
    password: ElementFinder;
    // loginButton: ElementFinder;

    constructor() {
        // this.fullname = element(by.id('fullname'));
        this.password = element(by.id('password'));
        // this.loginButton = element(by.id('login'));
    }

    async navigateTo(url: string): Promise<void> {
        /**
         * This is mandatory: `browser.waitForAngularEnabled(false);`
         *
         * Without `browser.waitForAngularEnabled(false);`, or set the value to true, then there will be the following two errors:
         * Error 1 of 2 (This error can happen sporadically, even though with `browser.waitForAngularEnabled(false);`):
         * [60148:82520:0424/154936.133:ERROR:device_event_log_impl.cc(214)] [15:49:36.133] USB: usb_device_handle
         * win.cc:1054 Failed to read descriptor from node connection: A device attached to the system is not functioning. (0x1F)
         * Error 2 of 2:
         * Some of the steps will fail to execute.
         */
        await browser.waitForAngularEnabled(false);
        await browser.get(url);
        /**
         * If not sleep here, there will be strange error of:
         *
         * [80972:47464:0424/154712.080:ERROR:device_event_log_impl.cc(214)] [15:47:12.080]
         * USB: usb_device_handle_win.cc:1054 Failed to read descriptor from node connection: A device attached to
         * the system is not functioning. (0x1F).
         */
        await browser.sleep(4_000);
    }

    async setFullname(fullname: string): Promise<void> {
        const elementFinder = element(by.id('fullname'));
        await browser.wait(until.presenceOf(elementFinder), 4_000, 'No input for fullname');
        await elementFinder.sendKeys(fullname);
        await browser.sleep(1_000);
    }

    async setPassword(password: string): Promise<void> {
        await this.password.sendKeys(password);
        await browser.sleep(2_000);
    }

    async sendEnterKey(): Promise<void> {
        const elementFinder = element(by.id('login'));
        await browser.wait(until.presenceOf(elementFinder), 4_000, 'No login button');
        await elementFinder.click();
        await browser.sleep(1_000);
    }

    async getPageSource(): Promise<string> {
        return await browser.getPageSource();
    }

    /**
     * Using `await browser.sleep(200);` instead of `setTimeout(() => {}, 200)`, so that the code
     * has the look and feel of synchronous, while it is actually asynchronous.
     */
    async closeAndQuit(): Promise<void> {
        // Sleep to avoid "UnhandledPromiseRejectionWarning: NoSuchSessionError: invalid session id"
        await browser.sleep(4_000);
        // 1. close first.
        await browser.close();
        // 2. then quit.
        await browser.quit();

        setTimeout(() => {
            // 1. close first.
            browser.close();
            // 2. then quit.
            browser.quit();
        }, 200);
    }
}
