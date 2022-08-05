import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeviceLevelDetailComponent } from './device-level-detail.component';

describe('DeviceLevel Management Detail Component', () => {
  let comp: DeviceLevelDetailComponent;
  let fixture: ComponentFixture<DeviceLevelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeviceLevelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ deviceLevel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DeviceLevelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DeviceLevelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load deviceLevel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.deviceLevel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
