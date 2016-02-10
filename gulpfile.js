'use strict';
const SRC_DIR = 'src/main/resources/static/resources';
const DEST_DIR = 'target/classes/static/resources';
const gulp = require('gulp');
const vulcanize = require('gulp-vulcanize');
const autoprefixer = require('gulp-autoprefixer');


gulp.task('vulcanize', function(){
  return gulp
    .src(SRC_DIR + '/elements.html')
    .pipe(vulcanize({
      inlineScripts: true,
      inlineCss: true
    }))
    .pipe(gulp.dest(DEST_DIR));
});

gulp.task('copy-resources', function(){
  return gulp.src(SRC_DIR+'/bower_components/webcomponentsjs/**/*')
    .pipe(gulp.dest(DEST_DIR+'/bower_components/webcomponentsjs'));
});

gulp.task('autoprefix', function(){
  return gulp.src(SRC_DIR + '/styles/**/*.css')
    .pipe(autoprefixer({
      browsers: ['last 2 versions'],
      cascade: false
    }))
    .pipe(gulp.dest(DEST_DIR+'/styles'));
});

gulp.task('default', ['vulcanize', 'copy-resources', 'autoprefix']);