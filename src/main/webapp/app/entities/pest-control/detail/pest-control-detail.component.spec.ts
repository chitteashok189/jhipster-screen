import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PestControlDetailComponent } from './pest-control-detail.component';

describe('PestControl Management Detail Component', () => {
  let comp: PestControlDetailComponent;
  let fixture: ComponentFixture<PestControlDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PestControlDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pestControl: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PestControlDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PestControlDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pestControl on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pestControl).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
