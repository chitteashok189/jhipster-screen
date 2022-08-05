import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RawMaterialDetailComponent } from './raw-material-detail.component';

describe('RawMaterial Management Detail Component', () => {
  let comp: RawMaterialDetailComponent;
  let fixture: ComponentFixture<RawMaterialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RawMaterialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rawMaterial: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RawMaterialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RawMaterialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rawMaterial on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rawMaterial).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
