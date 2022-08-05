import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LightDetailComponent } from './light-detail.component';

describe('Light Management Detail Component', () => {
  let comp: LightDetailComponent;
  let fixture: ComponentFixture<LightDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LightDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ light: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LightDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LightDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load light on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.light).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
