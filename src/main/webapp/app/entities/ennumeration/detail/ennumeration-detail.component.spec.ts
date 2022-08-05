import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnnumerationDetailComponent } from './ennumeration-detail.component';

describe('Ennumeration Management Detail Component', () => {
  let comp: EnnumerationDetailComponent;
  let fixture: ComponentFixture<EnnumerationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnnumerationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ennumeration: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EnnumerationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnnumerationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ennumeration on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ennumeration).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
