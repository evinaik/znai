const TestData = {

    graphvizColors: {
        a: {
            line: '#123752',
            fill: '#708EA4',
            text: '#eeeeee'
        },
        b: {
            line: '#AA8E39',
            fill: '#FFEAAA',
            text: '#806515'
        },
        c: {
            line: '#306E12',
            fill: "#519331",
            text: "#ABDD93"
        }
    },

    graphPresentation: {
        slides: [
            {
                ids: ['main', 'parse', 'main->parse'], "content": [
                {
                    "type": "Paragraph",
                    "content": [
                        {
                            "text": "hello",
                            "type": "SimpleText",
                            "content": []
                        }
                    ]
                }
            ]
            },
            {
                ids: ['listen'], "content": [
                {
                    "type": "Paragraph",
                    "content": [
                        {
                            "text": "hello world",
                            "type": "SimpleText",
                            "content": []
                        }
                    ]
                }
            ]
            }
        ]
    },

    svg: '<?xml version="1.0" encoding="UTF-8" standalone="no"?> <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN"  "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd"> <!-- Generated by graphviz version 2.38.0 (20140413.2041)  --> <!-- Title: Simple Pages: 1 --> <svg width="188pt" height="191pt"  viewBox="0.00 0.00 188.00 191.00" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"> <g id="graph0" class="graph" transform="scale(1 1) rotate(0) translate(4 187)"> <title>Simple</title> <polygon fill="white" stroke="none" points="-4,4 -4,-187 184,-187 184,4 -4,4"/> <!-- main --> <g id="node1" class="node"><title>main</title> <polygon fill="none" stroke="black" points="63,-146.5 63,-182.5 117,-182.5 117,-146.5 63,-146.5"/> <text text-anchor="middle" x="89.9966" y="-160.3" font-family="Times,serif" font-size="14.00">main [a]</text> </g> <!-- parse --> <g id="node2" class="node"><title>parse</title> <polygon fill="none" stroke="black" points="63,-73.5 63,-109.5 117,-109.5 117,-73.5 63,-73.5"/> <text text-anchor="middle" x="89.769" y="-87.3" font-family="Times,serif" font-size="14.00">parse</text> </g> <!-- main&#45;&gt;parse --> <g id="edge1" class="edge"><title>main&#45;&gt;parse</title> <path fill="none" stroke="black" d="M90,-146.455C90,-146.455 90,-119.59 90,-119.59"/> <polygon fill="black" stroke="black" points="93.5001,-119.59 90,-109.59 86.5001,-119.59 93.5001,-119.59"/> </g> <!-- listen --> <g id="node3" class="node"><title>listen</title> <polygon fill="none" stroke="black" points="0,-0.5 0,-36.5 54,-36.5 54,-0.5 0,-0.5"/> <text text-anchor="middle" x="26.6655" y="-14.3" font-family="Times,serif" font-size="14.00">listen</text> </g> <!-- parse&#45;&gt;listen --> <g id="edge2" class="edge"><title>parse&#45;&gt;listen</title> <path fill="none" stroke="black" d="M81,-73.4602C81,-50.0044 81,-12 81,-12 81,-12 64.3164,-12 64.3164,-12"/> <polygon fill="black" stroke="black" points="64.3164,-8.5001 54.3164,-12 64.3164,-15.5001 64.3164,-8.5001"/> </g> <!-- server --> <g id="node4" class="node"><title>server</title> <polygon fill="none" stroke="black" points="126,-0.5 126,-36.5 180,-36.5 180,-0.5 126,-0.5"/> <text text-anchor="middle" x="152.6" y="-14.3" font-family="Times,serif" font-size="14.00">server</text> </g> <!-- parse&#45;&gt;server --> <g id="edge3" class="edge"><title>parse&#45;&gt;server</title> <path fill="none" stroke="black" d="M99,-73.4112C99,-53.4169 99,-24 99,-24 99,-24 115.684,-24 115.684,-24"/> <polygon fill="black" stroke="black" points="115.684,-27.5001 125.684,-24 115.684,-20.5001 115.684,-27.5001"/> </g> </g> </svg> ',

    documentation: {
        "docMeta": {
            "logo": "img/two-sigma-logo.png",
            "type": "User Guide",
            "title": "VATS"
        },
        "toc": [
            {
                "sectionTitle": "Getting Started",
                "dirName": "getting-started",
                "items": [
                    {
                        "title": "Introduction",
                        "fileName": "introduction"
                    },
                    {
                        "title": "Setup",
                        "fileName": "setup"
                    },
                    {
                        "title": "First Code Base",
                        "fileName": "first-code-base"
                    }
                ]
            },
            {
                "sectionTitle": "Cook Book",
                "dirName": "cook-book",
                "items": [
                    {
                        "title": "Merging Time Series",
                        "fileName": "merging-time-series"
                    }
                ]
            },
            {
                "sectionTitle": "External Code Bases",
                "dirName": "external-code-bases",
                "items": [
                    {
                        "title": "External Code To Vats",
                        "fileName": "external-code-to-vats"
                    },
                    {
                        "title": "Maven Import",
                        "fileName": "maven-import"
                    },
                    {
                        "title": "License Restrictions",
                        "fileName": "license-restrictions"
                    }
                ]
            }
            ],
        "page": {
            "type": "Page",
            "content": [
                {
                    "title": "description",
                    "type": "Section",
                    "content": [
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "The vats maven-import command allows one to automatically create an unlimited number of external codebases, each of which contains containing one jar, given a maven jar.",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "For example, if we wanted to import commons-lang3 from mvnrepository.com, we would vats maven-import using the SBT dependency line",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "vats maven-import --sbt \u0027\"org.apache.commons\" % \"commons-lang3\" % \"3.4\"\u0027",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "The command stores",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "lang": "",
                            "lineNumber": "",
                            "snippet": "the source jar in a folder \u003ccodebase_root\u003e/java/non-deployable under the original file name ending with -sources.jar\nthe POM file as \u003ccodebase_root\u003e/pom.xml\nthe library itself under  \u003ccodebase_root\u003e/java/lib\n",
                            "type": "Snippet"
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "If you want to use an external java library, find its Maven groupid, artifactid and version on the web site of the open source organization creating the library or on mvnrepository.com or search.maven.org.",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "Once you have this information called GAV coordinates (for Group/Artifact/Version), you can run vats maven-import.",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "vats maven-import is hard-wired to refuse any software with the Gnu Public License.",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "vats maven-import only imports the GAV\u0027s which are not already present in VATS.",
                                    "type": "SimpleText"
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "The software.mi files of the generated codebases contain the proper dependency information in the STANDARD_JARS key, and proper MAVEN_GROUPID, MAVEN_ARTIFACTID, and MAVEN_VERSION keys. See Software.mi Makepath And Friends for details.",
                                    "type": "SimpleText"
                                }
                            ]
                        }
                    ]
                },
                {
                    "title": "codebase naming",
                    "type": "Section",
                    "content": [
                        {
                            "type": "BlockQuote",
                            "content": [
                                {
                                    "type": "Paragraph",
                                    "content": [
                                        {
                                            "text": "The names of the new codebases created by this command will be built in the following way :",
                                            "type": "SimpleText"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "type": "Paragraph"
                        },
                        {
                            "type": "BlockQuote",
                            "content": [
                                {
                                    "type": "Paragraph",
                                    "content": [
                                        {
                                            "text": "By taking the original orgId, artifactId and version from the maven repository",
                                            "type": "SimpleText"
                                        },
                                        {
                                            "type": "SoftLineBreak"
                                        },
                                        {
                                            "text": "and replacing the characters which are not allowed in VATS by z.",
                                            "type": "SimpleText"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "type": "Paragraph",
                            "content": [
                                {
                                    "text": "We realize this naming convention is not ideal.",
                                    "type": "SimpleText"
                                },
                                {
                                    "type": "SoftLineBreak"
                                },
                                {
                                    "text": "VATS codebase names are used to generate identifiers in bash,",
                                    "type": "SimpleText"
                                },
                                {
                                    "type": "SoftLineBreak"
                                },
                                {
                                    "text": "so allowing dashes and periods in codebase names would have required changes in many scripts",
                                    "type": "SimpleText"
                                },
                                {
                                    "type": "SoftLineBreak"
                                },
                                {
                                    "text": "across the Two Sigma codebase.. We consulted with several subject matter experts and concluded that replacing dashes and periods with a z was the most reasonable option.",
                                    "type": "SimpleText"
                                }
                            ]
                        }
                    ]
                },
                {
                    "title": "Usage Scenario",
                    "type": "Section",
                    "content": [
                        {
                            "bulletMarker": "-",
                            "tight": false,
                            "type": "BulletList",
                            "content": [
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "The user determines that a new open source library is needed and finds its coordinates,",
                                                    "type": "SimpleText"
                                                },
                                                {
                                                    "type": "SoftLineBreak"
                                                },
                                                {
                                                    "text": "either on the web site of the library or on ",
                                                    "type": "SimpleText"
                                                },
                                                {
                                                    "anchor": "http://mvnrepository.com",
                                                    "type": "Link"
                                                },
                                                {
                                                    "text": " or ...",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "User executes a command",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        },
                                        {
                                            "bulletMarker": "-",
                                            "tight": true,
                                            "type": "BulletList",
                                            "content": [
                                                {
                                                    "type": "ListItem",
                                                    "content": [
                                                        {
                                                            "type": "Paragraph",
                                                            "content": [
                                                                {
                                                                    "text": "Using sbt-style import:  ",
                                                                    "type": "SimpleText"
                                                                }
                                                            ]
                                                        }
                                                    ]
                                                },
                                                {
                                                    "type": "ListItem",
                                                    "content": [
                                                        {
                                                            "type": "Paragraph",
                                                            "content": [
                                                                {
                                                                    "text": "Using maven-style import:  ",
                                                                    "type": "SimpleText"
                                                                }
                                                            ]
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "VATS finds (jars to import) \u003d (all maven dependency jars) - (maven jars already in VATS).",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "VATS finds all (jars to imports) in the internal mirror.",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "VATS creates new external codebases for each dependency jar.",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "The user runs another VATS command for code review concerning these new external codebases.",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    "type": "ListItem",
                                    "content": [
                                        {
                                            "type": "Paragraph",
                                            "content": [
                                                {
                                                    "text": "Once the review is approved, the user pushes the new codebases along with other changes possibly.",
                                                    "type": "SimpleText"
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ],
            "tocItem": {
                "sectionTitle": "External Code Bases",
                "pageTitle": "Maven Import",
                "fileName": "maven-import",
                "dirName": "external-code-bases"
            },
            "renderContext": {
                "nestLevel": 1
            }
        }
    }
};

export default TestData;