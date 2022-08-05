import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeviceModelDetailComponent } from './device-model-detail.component';

describe('DeviceModel Management Detail Component', () => {
  let comp: DeviceModelDetailComponent;
  let fixture: ComponentFixture<DeviceModelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeviceModelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ deviceModel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DeviceModelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DeviceModelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load deviceModel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.deviceModel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
