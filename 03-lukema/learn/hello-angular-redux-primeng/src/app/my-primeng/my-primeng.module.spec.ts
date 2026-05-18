import { MyPrimengModule } from './my-primeng.module';

describe('PrimengModule', () => {
  let primengModule: MyPrimengModule;

  beforeEach(() => {
    primengModule = new MyPrimengModule();
  });

  it('should create an instance', () => {
    expect(primengModule).toBeTruthy();
  });
});
