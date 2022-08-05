import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GrowBedDetailComponent } from './grow-bed-detail.component';

describe('GrowBed Management Detail Component', () => {
  let comp: GrowBedDetailComponent;
  let fixture: ComponentFixture<GrowBedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GrowBedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ growBed: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GrowBedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GrowBedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load growBed on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.growBed).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
