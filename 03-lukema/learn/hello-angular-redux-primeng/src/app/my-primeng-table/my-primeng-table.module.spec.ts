import { MyPrimengTableModule } from './my-primeng-table.module';

describe('MyPrimengTableModule', () => {
  let myPrimengTableModule: MyPrimengTableModule;

  beforeEach(() => {
    myPrimengTableModule = new MyPrimengTableModule();
  });

  it('should create an instance', () => {
    expect(myPrimengTableModule).toBeTruthy();
  });
});
