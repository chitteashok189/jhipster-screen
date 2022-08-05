import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnnumerationTypeDetailComponent } from './ennumeration-type-detail.component';

describe('EnnumerationType Management Detail Component', () => {
  let comp: EnnumerationTypeDetailComponent;
  let fixture: ComponentFixture<EnnumerationTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnnumerationTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ennumerationType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EnnumerationTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnnumerationTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ennumerationType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ennumerationType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
