import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RoleTypeDetailComponent } from './role-type-detail.component';

describe('RoleType Management Detail Component', () => {
  let comp: RoleTypeDetailComponent;
  let fixture: ComponentFixture<RoleTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RoleTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ roleType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RoleTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RoleTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load roleType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.roleType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
