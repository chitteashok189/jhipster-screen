import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BreederDetailComponent } from './breeder-detail.component';

describe('Breeder Management Detail Component', () => {
  let comp: BreederDetailComponent;
  let fixture: ComponentFixture<BreederDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BreederDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ breeder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BreederDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BreederDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load breeder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.breeder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
