import { HelloHoustonPage } from './app.po';

describe('hello-houston App', () => {
    let page: HelloHoustonPage;

    beforeEach(() => {
        page = new HelloHoustonPage();
    });

    it('should display welcome message', done => {
        page.navigateTo();
        page.getParagraphText()
            .then(msg => expect(msg).toEqual('Welcome to app!!'))
            .then(done, done.fail);
    });
});
