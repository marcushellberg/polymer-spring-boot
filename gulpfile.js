'use strict';
const SRC_DIR = 'src/main/resources/static/resources';
const DEST_DIR = 'target/classes/static/resources';
const gulp = require('gulp');
const $ = require('gulp-load-plugins')();
const postcss = require('gulp-html-postcss');

gulp.task('build', function() {
  return gulp
    .src(SRC_DIR + '/elements.html')
    .pipe($.vulcanize({
      inlineScripts: true,
      inlineCss: true
    }))
    .pipe($.crisper())
    .pipe($.if('*.js', $.uglify({
      preserveComments: 'some'
    })))
    .pipe($.if('*.html', $.htmlmin({
      customAttrAssign: [/\$=/],
      removeComments: true,
      collapseWhitespace: true
    })))
    .pipe(gulp.dest(DEST_DIR));
});

gulp.task('copy-resources', function() {
  return gulp.src(SRC_DIR + '/bower_components/webcomponentsjs/**/*')
    .pipe(gulp.dest(DEST_DIR + '/bower_components/webcomponentsjs'));
});

gulp.task('autoprefix', function() {
  return gulp.src(SRC_DIR + '/styles/**/*.css')
    .pipe($.autoprefixer({
      browsers: ['last 2 versions'],
      cascade: false
    }))
    .pipe(gulp.dest(DEST_DIR + '/styles'));
});

gulp.task('default', ['build', 'copy-resources', 'autoprefix']);