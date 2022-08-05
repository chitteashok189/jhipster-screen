import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DisorderDetailComponent } from './disorder-detail.component';

describe('Disorder Management Detail Component', () => {
  let comp: DisorderDetailComponent;
  let fixture: ComponentFixture<DisorderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DisorderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ disorder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DisorderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DisorderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load disorder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.disorder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
