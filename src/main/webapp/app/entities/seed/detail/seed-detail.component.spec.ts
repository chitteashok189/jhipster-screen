import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeedDetailComponent } from './seed-detail.component';

describe('Seed Management Detail Component', () => {
  let comp: SeedDetailComponent;
  let fixture: ComponentFixture<SeedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SeedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ seed: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SeedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SeedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load seed on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.seed).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
