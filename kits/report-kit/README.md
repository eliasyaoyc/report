# Report Kit
This kit support that generate echart picture(e.g. bar, line, pie etc.) and file creation.

# Supports
* Bar、Cross Bar
* Pie、Hollow Pie
* Line
* HTML
* Excel
* PDF
* Word

# Example
```java
// 1. Create report builder.

```

![](https://img.halfrost.com/Blog/ArticleTitleImage/4.gif)

# TODO List
- [x] lock-free scheduler.
- [ ] support map-reduce.
- [ ] container task.
- [ ] task tag.
- [x] support log.
- [ ] support metrics, e.g. prometheus etc.
- [ ] support alarm, e.g. dingding etc.
- [ ] support docker startup.
- [ ] support admin.
- [x] java client(internal use).
- [ ] improve that spontaneous metadata management.(current version through mysql.)

# Use-case
The concrete use-case, you can see test package that including three sub package
* Charts: all cases of generating diagrams(e.g. bar, line, pie etc.) 
* Files: all cases of generating files(e.g. excel, word, html etc.)
* Combination: The combination generating the two above

# License

Licensed under of either of

* MIT license ([LICENSE-MIT](./LICENSE-MIT) or http://opensource.org/licenses/MIT)